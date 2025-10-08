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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final BuyerRepository buyerRepository;
    private final BookRepository bookRepository;
    private final BuyerBookRepository buyerBookRepository;
    private final SellerRepository sellerRepository;
    private final PaymentService paymentService;

    @Transactional
    public List<BookBuyResponse> buyBooks(Long buyerId, List<Long> bookIds) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new BookException("Buyer not found"));

        List<BookBuyResponse> responses = new ArrayList<>();

        for (Long bookId : bookIds) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookException("Book not found: " + bookId));

            Seller seller = book.getSeller();

            if (book.getStock() < 1) {
                throw new BookException("Book is out of stock: " + book.getName());
            }

            double totalAmount = book.getAmount();

            if (buyer.getBalance() < totalAmount) {
                throw new BookException("Insufficient balance for book: " + book.getName());
            }

            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAmount(totalAmount);
            paymentRequest.setClient("LIBRARIAN");

            PaymentResponse paymentResponse = paymentService.makePayment(paymentRequest);
            if (paymentResponse == null || paymentResponse.getTransactionCode() == null) {
                throw new BookException("Payment failed for book: " + book.getName());
            }

            // Balance update
            buyer.setBalance(buyer.getBalance() - totalAmount);
            buyerRepository.save(buyer);

            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) + totalAmount);
            sellerRepository.save(seller);

            // BuyerBook yaratmaq
            BuyerBook buyerBook = new BuyerBook();
            buyerBook.setBuyer(buyer);
            buyerBook.setBook(book);
            buyerBook.setQuantity(1);
            buyerBook.setTransactionCode(paymentResponse.getTransactionCode());
            buyerBookRepository.save(buyerBook);

            // Stock update
            book.setStock(book.getStock() - 1);
            bookRepository.save(book);

            responses.add(new BookBuyResponse(
                    "Book purchased successfully! Transaction: " + paymentResponse.getTransactionCode(),
                    book.getName(),
                    1
            ));
        }

        return responses;
    }


    @Transactional
    public List<BookBuyResponse> returnBooks(Long buyerId, List<String> transactionCodes) {
        List<BookBuyResponse> responses = new ArrayList<>();

        for (String transactionCode : transactionCodes) {
            BuyerBook buyerBook = buyerBookRepository
                    .findByBuyerIdAndTransactionCode(buyerId, transactionCode)
                    .orElseThrow(() -> new BookException("Invalid transaction code: " + transactionCode));

            Buyer buyer = buyerBook.getBuyer();
            Book book = buyerBook.getBook();
            Seller seller = book.getSeller();

            double totalAmount = book.getAmount();

            PaymentResponse refundResponse = paymentService.refundPayment(transactionCode, totalAmount);
            if (refundResponse == null || refundResponse.getTransactionCode() == null) {
                throw new BookException("Refund failed for book: " + book.getName());
            }

            // Balance update
            buyer.setBalance((buyer.getBalance() != null ? buyer.getBalance() : 0) + totalAmount);
            buyerRepository.save(buyer);

            seller.setBalance((seller.getBalance() != null ? seller.getBalance() : 0) - totalAmount);
            sellerRepository.save(seller);

            // Stock update
            book.setStock(book.getStock() + 1);
            bookRepository.save(book);

            // BuyerBook delete
            buyerBookRepository.delete(buyerBook);

            responses.add(new BookBuyResponse(
                    "Book returned successfully! Refund Transaction: " + refundResponse.getTransactionCode(),
                    book.getName(),
                    1
            ));
        }

        return responses;
    }

}
