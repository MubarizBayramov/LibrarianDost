package Book.Book.LibrarianDost.service;

import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.entity.Seller;
import Book.Book.LibrarianDost.exception.BookException;
import Book.Book.LibrarianDost.repository.BookRepository;
import Book.Book.LibrarianDost.repository.SellerRepository;
import Book.Book.LibrarianDost.request.BookAddRequest;
import Book.Book.LibrarianDost.request.BookUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final BookRepository bookRepository;
    public SellerService(SellerRepository sellerRepository, BookRepository bookRepository) {
        this.sellerRepository = sellerRepository;
        this.bookRepository = bookRepository;
    }


    public List<Book> getSellerBooks(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new BookException("Seller not found", HttpStatus.NOT_FOUND));
        return seller.getBooks();
    }


    public Book addBookToSeller(Long sellerId, BookAddRequest request) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new BookException("Seller not found", HttpStatus.NOT_FOUND));

        Book book = new Book();
        book.setName(request.getName());
        book.setAuthor(request.getAuthor());
        book.setStock(request.getStock() != null ? request.getStock() : 10);
        book.setStock(request.getStock());
        book.setSeller(seller);
        seller.getBooks().add(book);
        return bookRepository.save(book);
    }


    public Book updateBook(Long sellerId, Long bookId, BookUpdateRequest updatedBook) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found", HttpStatus.NOT_FOUND));
        if (!book.getSeller().getId().equals(sellerId)) {
            throw new BookException("This book does not belong to this seller", HttpStatus.FORBIDDEN);
        }
        book.setName(updatedBook.getName());
        book.setAuthor(updatedBook.getAuthor());
        book.setPrice(updatedBook.getPrice());
       if (updatedBook.getStock() != null && updatedBook.getStock() > 0) {
            book.setStock(updatedBook.getStock());
        } else if (updatedBook.getStock() != null) {
            throw new BookException("Stock must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        return bookRepository.save(book);
    }



    public void deleteBook(Long sellerId, Long bookId, String confirm) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found", HttpStatus.NOT_FOUND));
        if (!book.getSeller().getId().equals(sellerId)) {
            throw new BookException("This book does not belong to this seller", HttpStatus.FORBIDDEN);
        }
        if (confirm == null || !confirm.equalsIgnoreCase("yes")) {
            List<Book> books = bookRepository.findBySellerId(sellerId);
            String bookList = books.stream()
                    .map(b -> b.getId() + " - " + b.getName())
                    .reduce("", (a, b) -> a + "\n" + b);
            throw new BookException(
                    "Book deletion cancelled. To delete, set confirm=yes. Your books:\n" + bookList,
                    HttpStatus.BAD_REQUEST
            );
        }
        bookRepository.delete(book);
    }

}
