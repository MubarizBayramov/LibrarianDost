package Book.Book.LibrarianDost.repository;

import Book.Book.LibrarianDost.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameContainingIgnoreCase(String name);
    List<Book> findByAmountBetween(Double minAmount, Double maxAmount);
    List<Book> findByNameContainingIgnoreCaseAndAmountBetween(String name, Double minAmount, Double maxAmount);
    List<Book> findBySeller_NameContainingIgnoreCase(String sellerName);
    List<Book> findByNameContainingIgnoreCaseAndSeller_NameContainingIgnoreCase(String name, String sellerName);
    List<Book> findByNameContainingIgnoreCaseAndAmountBetweenAndSeller_NameContainingIgnoreCase(
            String name, Double minAmount, Double maxAmount, String sellerName);
    List<Book> findBySellerId(Long sellerId);
}
