package Book.Book.LibrarianDost.controller;



import Book.Book.LibrarianDost.response.BookBuyResponse;
import Book.Book.LibrarianDost.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyers")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/{buyerId}/books/{bookId}")
    public BookBuyResponse buyBook(@PathVariable Long buyerId,
                                   @PathVariable Long bookId,
                                   @RequestParam(defaultValue = "1") int quantity) {
        return transactionService.buyBook(buyerId, bookId, quantity);
    }

    @DeleteMapping("/{buyerId}/books/{bookId}")
    public BookBuyResponse returnBook(@PathVariable Long buyerId,
                                      @PathVariable Long bookId,
                                      @RequestParam(defaultValue = "1") int quantity) {
        return transactionService.returnBook(buyerId, bookId, quantity);
    }
}
