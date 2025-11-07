package Book.Book.LibrarianDost.controller;

import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.request.BookAddRequest;
import Book.Book.LibrarianDost.request.BookUpdateRequest;
import Book.Book.LibrarianDost.response.BookAddResponse;
import Book.Book.LibrarianDost.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }


    @GetMapping("/{sellerId}/books")
    public List<Book> getSellerBooks(@PathVariable Long sellerId) {
        return sellerService.getSellerBooks(sellerId);
    }


    @PreAuthorize("hasRole('ROLE_ADD_BOOK')")
    @PostMapping("/{sellerId}/books")
    public BookAddResponse addBook(@PathVariable Long sellerId,
                                   @RequestBody @Valid BookAddRequest request) {
        Book savedBook = sellerService.addBookToSeller(sellerId, request);
        return new BookAddResponse(
                savedBook.getName(),
                savedBook.getAuthor(),
                savedBook.getAmount(),
                savedBook.getStock()
        );
    }


    @PreAuthorize("hasRole('ROLE_UPDATE_BOOK')")
    @PutMapping("/{sellerId}/books/{bookId}")
    public Book updateBook(@PathVariable Long sellerId,
                           @PathVariable Long bookId,
                           @RequestBody @Valid BookUpdateRequest updatedBook) {
        return sellerService.updateBook(sellerId, bookId, updatedBook);
    }


    @PreAuthorize("hasRole('ROLE_DELETE_BOOK')")
    @DeleteMapping("/{sellerId}/books/{bookId}")
    public String deleteBook(@PathVariable Long sellerId,
                             @PathVariable Long bookId,
                             @RequestParam(required = false) Integer quantity,
                             @RequestParam(required = false) String confirm) {
        sellerService.deleteBook(sellerId, bookId, quantity, confirm);
        return "Book deleted successfully!";
    }
}
