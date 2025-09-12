package Book.Book.LibrarianDost.controller;

import Book.Book.LibrarianDost.response.BookBuyResponse;
import Book.Book.LibrarianDost.response.BookResponse;
import Book.Book.LibrarianDost.response.MessageResponse;
import Book.Book.LibrarianDost.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyers")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;

    @GetMapping("/books")
    public List<BookResponse> getAllBooks() {
        return buyerService.getAllBooks();
    }

    @PostMapping("/{buyerId}/books/{bookId}")
    public BookBuyResponse buyBook(@PathVariable Long buyerId,
                                   @PathVariable Long bookId,
                                   @RequestParam(defaultValue = "1") int quantity) {
        return buyerService.buyBook(buyerId, bookId, quantity);
    }

    @DeleteMapping("/{buyerId}/books/{bookId}")
    public MessageResponse returnBook(@PathVariable Long buyerId,
                                      @PathVariable Long bookId,
                                      @RequestParam(defaultValue = "1") int quantity) {
        buyerService.returnBook(buyerId, bookId, quantity);
        return new MessageResponse("Book returned successfully!");
    }

    @GetMapping("/books/search")
    public List<BookResponse> searchBooks(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) Double minPrice,
                                          @RequestParam(required = false) Double maxPrice,
                                          @RequestParam(required = false) String sellerName) {
        return buyerService.searchBooks(name, minPrice, maxPrice, sellerName);
    }
}
