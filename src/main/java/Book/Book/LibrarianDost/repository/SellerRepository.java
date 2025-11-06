package Book.Book.LibrarianDost.repository;

import Book.Book.LibrarianDost.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {


    Optional<Seller> findByName(String name);


    boolean existsByName(String name);
}
