package Book.Book.LibrarianDost.service;

import Book.Book.LibrarianDost.repository.BookRepository;
import Book.Book.LibrarianDost.repository.BuyerBookRepository;
import Book.Book.LibrarianDost.repository.BuyerRepository;
import Book.Book.LibrarianDost.repository.SellerRepository;
import com.common.dto.PaymentRequest;
import com.common.dto.PaymentResponse;
import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.entity.Buyer;
import Book.Book.LibrarianDost.entity.BuyerBook;
import Book.Book.LibrarianDost.entity.Seller;
import Book.Book.LibrarianDost.exception.BookException;
import Book.Book.LibrarianDost.response.BookBuyResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final BuyerRepository buyerRepository;
    private final BookRepository bookRepository;
    private final BuyerBookRepository buyerBookRepository;
    private final SellerRepository sellerRepository;
    private final PaymentService paymentService;


    public BookBuyResponse buyBook(Long buyerId, Long bookId, int quantity) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BookException("Buyer not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found"));

        if (book.getStock() < quantity) {
            throw new BookException("Not enough stock!");
        }

        double totalAmount = book.getAmount() * quantity;

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(totalAmount);
        paymentRequest.setClient("LIBRARIAN");

        PaymentResponse paymentResponse = paymentService.pay(paymentRequest);
        if (paymentResponse == null || paymentResponse.getTransactionId() == null) {
            throw new BookException("Payment failed!");
        }

        // Balans yeniləmələri
        buyer.setBalance(buyer.getBalance() - totalAmount);
        buyerRepository.save(buyer);

        Seller seller = book.getSeller();
        if (seller != null) {
            double sellerIncome = totalAmount * 0.99; // 1% komissiya
            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) + sellerIncome);
            sellerRepository.save(seller);
        }

        // Kitab stokunu yenilə
        book.setStock(book.getStock() - quantity);
        book.setMarker("-" + quantity);
        bookRepository.save(book);

        // BuyerBook yaz
        BuyerBook buyerBook = buyerBookRepository.findByBuyerAndBook(buyer, book)
                .orElse(new BuyerBook());
        Integer existingQuantity = buyerBook.getQuantity();
        buyerBook.setBuyer(buyer);
        buyerBook.setBook(book);
        buyerBook.setQuantity((existingQuantity != null ? existingQuantity : 0) + quantity);
        buyerBook.setTransactionCode(paymentResponse.getTransactionId());
        buyerBookRepository.save(buyerBook);

        return new BookBuyResponse(
                "Book purchased successfully! Transaction: " + paymentResponse.getTransactionId(),
                book.getName(),
                quantity,
                book.getMarker()
        );
    }

    public BookBuyResponse returnBook(Long buyerId, Long bookId, int quantity) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BookException("Buyer not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found"));

        BuyerBook buyerBook = buyerBookRepository.findByBuyerAndBook(buyer, book)
                .orElseThrow(() -> new BookException("This book was not bought by the buyer"));

        if (buyerBook.getQuantity() < quantity) {
            throw new BookException("Return quantity is greater than bought quantity");
        }

        double totalAmount = book.getAmount() * quantity;

        PaymentResponse refundResponse = paymentService.refundBook(buyerBook.getTransactionCode(), totalAmount);
        if (refundResponse == null || refundResponse.getTransactionId() == null) {
            throw new BookException("Refund failed!");
        }

        // Balans yeniləmələri
        buyer.setBalance(buyer.getBalance() + totalAmount);
        buyerRepository.save(buyer);

        Seller seller = book.getSeller();
        if (seller != null) {
            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) - totalAmount);
            sellerRepository.save(seller);
        }

        // Kitab stokunu artır
        book.setStock(book.getStock() + quantity);
        book.setMarker("+" + quantity);
        bookRepository.save(book);

        // BuyerBook yenilə
        buyerBook.setQuantity(buyerBook.getQuantity() - quantity);
        if (buyerBook.getQuantity() == 0) {
            buyerBookRepository.delete(buyerBook);
        } else {
            buyerBookRepository.save(buyerBook);
        }

        return new BookBuyResponse(
                "Book returned successfully! Refund Transaction: " + refundResponse.getTransactionId(),
                book.getName(),
                quantity,
                book.getMarker()
        );
    }
}
