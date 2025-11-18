package Book.Book.LibrarianDost.repository;

import Book.Book.LibrarianDost.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findByName(String name);
}
