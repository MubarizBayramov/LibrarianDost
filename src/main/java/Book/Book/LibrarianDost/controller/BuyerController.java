package Book.Book.LibrarianDost.controller;


import Book.Book.LibrarianDost.response.BookResponse;
import Book.Book.LibrarianDost.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/search")
    public List<BookResponse> searchBooks(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) Double minAmount,
                                          @RequestParam(required = false) Double maxAmount,
                                          @RequestParam(required = false) String sellerName) {
        return buyerService.searchBooks(name, minAmount, maxAmount, sellerName);
    }


}
