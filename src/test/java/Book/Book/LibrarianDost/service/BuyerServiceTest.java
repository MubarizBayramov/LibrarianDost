package Book.Book.LibrarianDost.service;
import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.entity.Seller;
import Book.Book.LibrarianDost.repository.BookRepository;
import Book.Book.LibrarianDost.repository.BuyerRepository;
import Book.Book.LibrarianDost.response.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BuyerServiceTest {

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BuyerService buyerService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Seller seller = new Seller();
        seller.setId(1L);

        book = new Book();
        book.setId(10L);
        book.setName("Java Programming");
        book.setAuthor("Mubariz");
        book.setPrice(50.0);
        book.setSeller(seller);
    }

    @Test
    void testSearchBooks_ByName() {
        when(bookRepository.findByNameContainingIgnoreCase("Java"))
                .thenReturn(List.of(book));

        List<BookResponse> result = buyerService.searchBooks("Java", null, null,  null);

        assertEquals(1, result.size());
        assertEquals("Java Programming", result.get(0).getName());
        assertEquals("Mubariz", result.get(0).getAuthor());
        assertEquals(50.0, result.get(0).getPrice());
        assertEquals(1L, result.get(0).getSellerId());
    }

    @Test
    void testSearchBooks_AllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookResponse> result = buyerService.searchBooks(null, null, null);

        assertEquals(1, result.size());
        assertEquals("Java Programming", result.get(0).getName());
    }
}
