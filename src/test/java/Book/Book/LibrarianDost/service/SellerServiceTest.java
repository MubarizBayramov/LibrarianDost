package Book.Book.LibrarianDost.service;



import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.entity.Seller;
import Book.Book.LibrarianDost.exception.BookException;
import Book.Book.LibrarianDost.repository.BookRepository;
import Book.Book.LibrarianDost.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SellerServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private SellerService sellerService;

    private Seller seller;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book1 = new Book();
        book1.setId(1L);

        book2 = new Book();
        book2.setId(2L);

        seller = new Seller();
        seller.setId(100L);
        seller.setBooks(List.of(book1, book2));
    }

    @Test
    void testGetSellerBooks_Success() {
        when(sellerRepository.findById(100L)).thenReturn(Optional.of(seller));

        List<Book> result = sellerService.getSellerBooks(100L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book2));
    }

    @Test
    void testGetSellerBooks_SellerNotFound() {
        when(sellerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> sellerService.getSellerBooks(999L));
    }
}
