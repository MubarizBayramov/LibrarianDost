package Book.Book.LibrarianDost.service;

import Book.Book.LibrarianDost.constant.Clients;
import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.entity.Buyer;
import Book.Book.LibrarianDost.entity.BuyerBook;
import Book.Book.LibrarianDost.entity.Seller;
import Book.Book.LibrarianDost.exception.BookException;
import Book.Book.LibrarianDost.repository.BookRepository;
import Book.Book.LibrarianDost.repository.BuyerBookRepository;
import Book.Book.LibrarianDost.repository.BuyerRepository;
import Book.Book.LibrarianDost.repository.SellerRepository;
import Book.Book.LibrarianDost.request.PaymentRequest;
import Book.Book.LibrarianDost.response.BookBuyResponse;
import Book.Book.LibrarianDost.response.BookResponse;
import Book.Book.LibrarianDost.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final BookRepository bookRepository;
    private final BuyerBookRepository buyerBookRepository;
    private final SellerRepository sellerRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository,
                        BookRepository bookRepository,
                        BuyerBookRepository buyerBookRepository,
                        SellerRepository sellerRepository,
                        RestTemplate restTemplate) {
        this.buyerRepository = buyerRepository;
        this.bookRepository = bookRepository;
        this.buyerBookRepository = buyerBookRepository;
        this.sellerRepository = sellerRepository;
        this.restTemplate = restTemplate;
    }


    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getName(),
                        book.getAuthor(),
                        book.getPrice(),
                        book.getSeller() != null ? book.getSeller().getId() : null
                ))
                .toList();
    }


    public BookBuyResponse buyBook(Long buyerId, Long bookId, int quantity) {
        // 1. Buyer və kitabı tap
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BookException("Buyer not found", HttpStatus.NOT_FOUND));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found", HttpStatus.NOT_FOUND));

        if (book.getStock() < quantity) {
            throw new BookException("Not enough stock!", HttpStatus.BAD_REQUEST);
        }


        double totalPrice = book.getPrice() * quantity;
        double commission = totalPrice * 0.01;
        double sellerIncome = totalPrice - commission;


        PaymentRequest paymentRequest = new PaymentRequest(
                totalPrice,
                Clients.LIBRARIAN,
                buyer.getId().toString(),
                book.getSeller().getId().toString()
        );

        ResponseEntity<PaymentResponse> responseEntity =
                restTemplate.postForEntity(
                        "http://localhost:8880/payments",
                        paymentRequest,
                        PaymentResponse.class
                );

        PaymentResponse paymentResponse = responseEntity.getBody();
        if (paymentResponse == null || paymentResponse.getTransactionCode() == null) {
            throw new BookException("Payment failed!", HttpStatus.BAD_REQUEST);
        }


        buyer.setBalance(buyer.getBalance() - totalPrice);
        buyerRepository.save(buyer);


        Seller seller = book.getSeller();
        if (seller != null) {
            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) + sellerIncome);
            sellerRepository.save(seller);
        }


        book.setStock(book.getStock() - quantity);
        book.setMarker("-" + quantity);
        bookRepository.save(book);


        BuyerBook buyerBook = buyerBookRepository.findByBuyerAndBook(buyer, book)
                .orElse(new BuyerBook());
        buyerBook.setBuyer(buyer);
        buyerBook.setBook(book);
        buyerBook.setQuantity(buyerBook.getQuantity() + quantity);
        buyerBookRepository.save(buyerBook);


        return new BookBuyResponse(
                "Book purchased successfully! Transaction: " + paymentResponse.getTransactionCode(),
                book.getName(),
                quantity,
                book.getMarker()
        );
    }



    public BookBuyResponse returnBook(Long buyerId, Long bookId, int quantity) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BookException("Buyer not found", HttpStatus.NOT_FOUND));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found", HttpStatus.NOT_FOUND));
        BuyerBook buyerBook = buyerBookRepository.findByBuyerAndBook(buyer, book)
                .orElseThrow(() -> new BookException("This book was not bought by the buyer", HttpStatus.BAD_REQUEST));

        if (buyerBook.getQuantity() < quantity) {
            throw new BookException("Return quantity is greater than bought quantity", HttpStatus.BAD_REQUEST);
        }

        double totalPrice = book.getPrice() * quantity;

       buyer.setBalance(buyer.getBalance() + totalPrice);
        buyerRepository.save(buyer);

        Seller seller = book.getSeller();
        if (seller != null) {
            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) - totalPrice);
            sellerRepository.save(seller);
        }


        PaymentRequest refundRequest = new PaymentRequest(
                -totalPrice,
                Clients.LIBRARIAN,
                buyer.getId().toString(),
                seller != null ? seller.getId().toString() : null
        );

        ResponseEntity<PaymentResponse> refundResponse = restTemplate.postForEntity(
                "http://localhost:8880/payments",
                refundRequest,
                PaymentResponse.class
        );

        if (refundResponse.getBody() == null || refundResponse.getBody().getTransactionCode() == null) {
            throw new BookException("Refund failed!", HttpStatus.BAD_REQUEST);
        }


        buyerBook.setQuantity(buyerBook.getQuantity() - quantity);
        book.setStock(book.getStock() + quantity);
        book.setMarker("+" + quantity);
        bookRepository.save(book);

        if (buyerBook.getQuantity() == 0) {
            buyerBookRepository.delete(buyerBook);
        } else {
            buyerBookRepository.save(buyerBook);
        }

        return new BookBuyResponse(
                "Book returned successfully! Refund Transaction: " + refundResponse.getBody().getTransactionCode(),
                book.getName(),
                quantity,
                book.getMarker()
        );
    }



    public List<BookResponse> searchBooks(String name, Double minPrice, Double maxPrice, String sellerName) {
        List<Book> books;

        if (name != null && minPrice != null && maxPrice != null && sellerName != null) {
            books = bookRepository.findByNameContainingIgnoreCaseAndPriceBetweenAndSeller_NameContainingIgnoreCase(
                    name, minPrice, maxPrice, sellerName);
        } else if (name != null && sellerName != null) {
            books = bookRepository.findByNameContainingIgnoreCaseAndSeller_NameContainingIgnoreCase(name, sellerName);
        } else if (sellerName != null) {
            books = bookRepository.findBySeller_NameContainingIgnoreCase(sellerName);
        } else if (name != null && minPrice != null && maxPrice != null) {
            books = bookRepository.findByNameContainingIgnoreCaseAndPriceBetween(name, minPrice, maxPrice);
        } else if (name != null) {
            books = bookRepository.findByNameContainingIgnoreCase(name);
        } else if (minPrice != null && maxPrice != null) {
            books = bookRepository.findByPriceBetween(minPrice, maxPrice);
        } else {
            books = bookRepository.findAll();
        }

        return books.stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getName(),
                        book.getAuthor(),
                        book.getPrice(),
                        book.getSeller() != null ? book.getSeller().getId() : null
                ))
                .toList();
    }
}
