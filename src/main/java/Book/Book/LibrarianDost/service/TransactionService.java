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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final BuyerRepository buyerRepository;
    private final BookRepository bookRepository;
    private final BuyerBookRepository buyerBookRepository;
    private final SellerRepository sellerRepository;
    private final PaymentService paymentService;

    // Hər əməliyyat üçün ayrıca transactionCode
    public BookBuyResponse buyBook(Long buyerId, Long bookId, int quantity) {
        if (quantity != 1) {
            throw new BookException("You can buy only one book per transaction!");
        }

        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BookException("Buyer not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found"));

        if (book.getStock() < 1) {
            throw new BookException("Book is out of stock!");
        }

        double totalAmount = book.getAmount();

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(totalAmount);
        paymentRequest.setClient("LIBRARIAN");

        PaymentResponse paymentResponse = paymentService.pay(paymentRequest);
        if (paymentResponse == null || paymentResponse.getTransactionId() == null) {
            throw new BookException("Payment failed!");
        }

        // Unikal əməliyyat kodu
        String transactionCode = UUID.randomUUID().toString();

        // Balans yenilə
        buyer.setBalance(buyer.getBalance() - totalAmount);
        buyerRepository.save(buyer);

        Seller seller = book.getSeller();
        if (seller != null) {
            double sellerIncome = totalAmount * 0.99; // 1% komissiya
            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) + sellerIncome);
            sellerRepository.save(seller);
        }

        // Stok azaldılır
        book.setStock(book.getStock() - 1);
        book.setMarker("-1");
        bookRepository.save(book);

        // BuyerBook-a yeni əməliyyat kimi yaz
        BuyerBook buyerBook = new BuyerBook();
        buyerBook.setBuyer(buyer);
        buyerBook.setBook(book);
        buyerBook.setQuantity(1);
        buyerBook.setTransactionCode(transactionCode);
        buyerBookRepository.save(buyerBook);

        return new BookBuyResponse(
                "Book purchased successfully! Transaction: " + transactionCode,
                book.getName(),
                1,
                "-1"
        );
    }

    public BookBuyResponse returnBook(Long buyerId, Long bookId, int quantity, String transactionCode) {
        if (quantity != 1) {
            throw new BookException("You can return only one book per transaction!");
        }

        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BookException("Buyer not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found"));

        BuyerBook buyerBook = buyerBookRepository.findByBuyerAndBookAndTransactionCode(buyer, book, transactionCode)
                .orElseThrow(() -> new BookException("Invalid transaction code for this book or buyer"));

        double totalAmount = book.getAmount();

        PaymentResponse refundResponse = paymentService.refundBook(transactionCode, totalAmount);
        if (refundResponse == null || refundResponse.getTransactionId() == null) {
            throw new BookException("Refund failed!");
        }

        // Balans geri qaytarılır
        buyer.setBalance(buyer.getBalance() + totalAmount);
        buyerRepository.save(buyer);

        Seller seller = book.getSeller();
        if (seller != null) {
            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) - totalAmount);
            sellerRepository.save(seller);
        }

        // Stok artırılır
        book.setStock(book.getStock() + 1);
        book.setMarker("+1");
        bookRepository.save(book);

        // BuyerBook silinir
        buyerBookRepository.delete(buyerBook);

        return new BookBuyResponse(
                "Book returned successfully! Refund Transaction: " + refundResponse.getTransactionId(),
                book.getName(),
                1,
                "+1"
        );
    }

}
