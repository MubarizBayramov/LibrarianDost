package Book.Book.LibrarianDost.repository;

import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.entity.Buyer;
import Book.Book.LibrarianDost.entity.BuyerBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerBookRepository extends JpaRepository<BuyerBook, Long> {
    Optional<BuyerBook> findByBuyerAndBook(Buyer buyer, Book book);
}
