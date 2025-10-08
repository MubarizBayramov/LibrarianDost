package Book.Book.LibrarianDost.controller;

import Book.Book.LibrarianDost.response.BookBuyResponse;
import Book.Book.LibrarianDost.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyers")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    @PostMapping("/{buyerId}/books")
    public List<BookBuyResponse> buyBooks(@PathVariable Long buyerId,
                                          @RequestBody List<Long> bookIds) {
        return transactionService.buyBooks(buyerId, bookIds);
    }


    @DeleteMapping("/{buyerId}/books/transactions")
    public List<BookBuyResponse> returnBooks(@PathVariable Long buyerId,
                                             @RequestBody List<String> transactionCodes) {
        return transactionService.returnBooks(buyerId, transactionCodes);
    }
}
