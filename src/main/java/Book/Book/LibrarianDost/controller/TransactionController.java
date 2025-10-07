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
                                   @PathVariable Long bookId) {
        return transactionService.buyBook(buyerId, bookId);
    }


    @DeleteMapping("/{buyerId}/books/{bookId}/transaction/{transactionCode}")
    public BookBuyResponse returnBook(@PathVariable Long buyerId,
                                      @PathVariable Long bookId,
                                      @PathVariable String transactionCode) {
        return transactionService.returnBook(buyerId, bookId, transactionCode);
    }

}
