package Book.Book.LibrarianDost.repository;

import Book.Book.LibrarianDost.entity.BuyerBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerBookRepository extends JpaRepository<BuyerBook, Long> {


    Optional<BuyerBook> findByBuyerIdAndTransactionCode(Long buyerId, String transactionCode);
}
