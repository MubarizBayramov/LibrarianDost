package Book.Book.LibrarianDost.repository;

import Book.Book.LibrarianDost.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameContainingIgnoreCase(String name);
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Book> findByNameContainingIgnoreCaseAndPriceBetween(String name, Double minPrice, Double maxPrice);
    List<Book> findBySeller_NameContainingIgnoreCase(String sellerName);
    List<Book> findByNameContainingIgnoreCaseAndSeller_NameContainingIgnoreCase(String name, String sellerName);
    List<Book> findByNameContainingIgnoreCaseAndPriceBetweenAndSeller_NameContainingIgnoreCase(
            String name, Double minPrice, Double maxPrice, String sellerName);
    List<Book> findBySellerId(Long sellerId);


}
