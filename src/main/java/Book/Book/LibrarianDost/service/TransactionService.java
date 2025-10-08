package Book.Book.LibrarianDost.service;

import com.common.dto.PaymentRequest;
import com.common.dto.PaymentResponse;
import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.entity.Buyer;
import Book.Book.LibrarianDost.entity.BuyerBook;
import Book.Book.LibrarianDost.entity.Seller;
import Book.Book.LibrarianDost.exception.BookException;
import Book.Book.LibrarianDost.repository.BookRepository;
import Book.Book.LibrarianDost.repository.BuyerBookRepository;
import Book.Book.LibrarianDost.repository.BuyerRepository;
import Book.Book.LibrarianDost.repository.SellerRepository;
import Book.Book.LibrarianDost.response.BookBuyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final BuyerRepository buyerRepository;
    private final BookRepository bookRepository;
    private final BuyerBookRepository buyerBookRepository;
    private final SellerRepository sellerRepository;
    private final PaymentService paymentService;

    @Transactional
    public BookBuyResponse buyBook(Long buyerId, Long bookId) {

        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BookException("Buyer not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found"));
        Seller seller = book.getSeller();

        if (book.getStock() < 1) {
            throw new BookException("Book is out of stock!");
        }

        double totalAmount = book.getAmount();


        if (buyer.getBalance() < totalAmount) {
            throw new BookException("Insufficient balance!");
        }


        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(totalAmount);
        paymentRequest.setClient("LIBRARIAN");

        PaymentResponse paymentResponse = paymentService.makePayment(paymentRequest);
        if (paymentResponse == null || paymentResponse.getTransactionCode() == null) {
            throw new BookException("Payment failed!");
        }


        buyer.setBalance(buyer.getBalance() - totalAmount);
        buyerRepository.save(buyer);

            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) + totalAmount);
            sellerRepository.save(seller);



        BuyerBook buyerBook = new BuyerBook();
        buyerBook.setBuyer(buyer);
        buyerBook.setBook(book);
        buyerBook.setQuantity(1);
        buyerBook.setTransactionCode(paymentResponse.getTransactionCode());
        buyerBookRepository.save(buyerBook);


        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        return new BookBuyResponse(
                "Book purchased successfully! Transaction: " + paymentResponse.getTransactionCode(),
                book.getName(),
                1
        );
    }

    @Transactional
    public BookBuyResponse returnBook(Long buyerId, Long bookId, String transactionCode) {

        BuyerBook buyerBook = buyerBookRepository
                .findByBuyerIdAndBookIdAndTransactionCode(buyerId, bookId, transactionCode)
                .orElseThrow(() -> new BookException("Invalid transaction code for this book or buyer"));

        Buyer buyer = buyerBook.getBuyer();
        Book book = buyerBook.getBook();
        Seller seller = book.getSeller();

        double totalAmount = book.getAmount();

        PaymentResponse refundResponse = paymentService.refundPayment(transactionCode, totalAmount);
        if (refundResponse == null || refundResponse.getTransactionCode() == null) {
            throw new BookException("Refund failed!");
        }


        buyer.setBalance((buyer.getBalance() != null ? buyer.getBalance() : 0) + totalAmount);
        buyerRepository.save(buyer);



            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) - totalAmount);
            sellerRepository.save(seller);



        book.setStock(book.getStock() + 1);
        bookRepository.save(book);


        buyerBookRepository.delete(buyerBook);

        return new BookBuyResponse(
                "Book returned successfully! Refund Transaction: " + refundResponse.getTransactionCode(),
                book.getName(),
                1
        );
    }
}
