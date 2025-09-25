package Book.Book.LibrarianDost.controller;



import Book.Book.LibrarianDost.response.BookBuyResponse;
import Book.Book.LibrarianDost.service.BuyerTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyers")
@RequiredArgsConstructor
public class BuyerTransactionController {

    private final BuyerTransactionService buyerTransactionService;

    @PostMapping("/{buyerId}/books/{bookId}")
    public BookBuyResponse buyBook(@PathVariable Long buyerId,
                                   @PathVariable Long bookId,
                                   @RequestParam(defaultValue = "1") int quantity) {
        return buyerTransactionService.buyBook(buyerId, bookId, quantity);
    }

    @DeleteMapping("/{buyerId}/books/{bookId}")
    public BookBuyResponse returnBook(@PathVariable Long buyerId,
                                      @PathVariable Long bookId,
                                      @RequestParam(defaultValue = "1") int quantity) {
        return buyerTransactionService.returnBook(buyerId, bookId, quantity);
    }
}
