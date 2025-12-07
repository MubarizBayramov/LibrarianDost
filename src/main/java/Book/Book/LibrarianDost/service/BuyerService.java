package Book.Book.LibrarianDost.service;

import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.repository.BookRepository;
import Book.Book.LibrarianDost.response.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor


public class BuyerService {

    private final BookRepository bookRepository;

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getName(),
                        book.getAuthor(),
                        book.getAmount(),
                        book.getSeller() != null ? book.getSeller().getId() : null
                )).toList();
    }

    public List<BookResponse> searchBooks(String name, Double minAmount, Double maxAmount, String sellerName) {
        List<Book> books;

        if (name != null && minAmount != null && maxAmount != null && sellerName != null) {
            books = bookRepository.findByNameContainingIgnoreCaseAndAmountBetweenAndSeller_NameContainingIgnoreCase(
                    name, minAmount, maxAmount, sellerName);
        } else if (name != null && sellerName != null) {
            books = bookRepository.findByNameContainingIgnoreCaseAndSeller_NameContainingIgnoreCase(name, sellerName);
        } else if (sellerName != null) {
            books = bookRepository.findBySeller_NameContainingIgnoreCase(sellerName);
        } else if (name != null && minAmount != null && maxAmount != null) {
            books = bookRepository.findByNameContainingIgnoreCaseAndAmountBetween(name, minAmount, maxAmount);
        } else if (name != null) {
            books = bookRepository.findByNameContainingIgnoreCase(name);
        } else if (minAmount != null && maxAmount != null) {
            books = bookRepository.findByAmountBetween(minAmount, maxAmount);
        } else {
            books = bookRepository.findAll();
        }
        return books.stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getName(),
                        book.getAuthor(),
                        book.getAmount(),
                        book.getSeller() != null ? book.getSeller().getId() : null
                )).toList();
    }
}
