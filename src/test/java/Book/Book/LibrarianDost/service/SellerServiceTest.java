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
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SellerServiceGetBooksTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private SellerService sellerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSellerBooks_Hits3() {

        Seller seller = new Seller();
        seller.setId(1L);

        Book book1 = new Book();
        book1.setName("Java Basics");

        Book book2 = new Book();
        book2.setName("Spring Boot");

        Book book3 = new Book();
        book3.setName("Hibernate ORM");

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        seller.setBooks(books);


        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        // Metodu çağır
        List<Book> result = sellerService.getSellerBooks(1L);

        // Yoxlamalar
        assertEquals(3, result.size()); // hits = 3
        assertEquals("Java Basics", result.get(0).getName());
        assertEquals("Spring Boot", result.get(1).getName());
        assertEquals("Hibernate ORM", result.get(2).getName());

        verify(sellerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSellerBooks_SellerNotFound() {
        when(sellerRepository.findById(2L)).thenReturn(Optional.empty());

        BookException exception = assertThrows(BookException.class, () -> {
            sellerService.getSellerBooks(2L);
        });

        assertEquals("Seller not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        verify(sellerRepository, times(1)).findById(2L);
    }


        @Test
        void testDeleteBook_SuccessWithQuantity() {
            Seller seller = new Seller();
            seller.setId(1L);

            Book book = new Book();
            book.setId(100L);
            book.setName("Java Basics");
            book.setStock(5);
            book.setSeller(seller);

            when(bookRepository.findById(100L)).thenReturn(Optional.of(book));
            when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);

            // Quantity ilə sil
            sellerService.deleteBook(1L, 100L, 2, "yes");

            assertEquals(3, book.getStock()); // stock azaldılıb
            verify(bookRepository, times(1)).save(book);
            verify(bookRepository, never()).delete(book);
        }

        @Test
        void testDeleteBook_ConfirmNull_Hits2() {
            Seller seller = new Seller();
            seller.setId(1L);

            Book book1 = new Book();
            book1.setId(101L);
            book1.setName("Spring Boot");
            book1.setStock(10);
            book1.setSeller(seller);

            Book book2 = new Book();
            book2.setId(102L);
            book2.setName("Hibernate ORM");
            book2.setStock(5);
            book2.setSeller(seller);

            List<Book> sellerBooks = new ArrayList<>();
            sellerBooks.add(book1);
            sellerBooks.add(book2);

            when(bookRepository.findById(101L)).thenReturn(Optional.of(book1));
            when(bookRepository.findBySellerId(1L)).thenReturn(sellerBooks);

            BookException exception = assertThrows(BookException.class, () -> {
                sellerService.deleteBook(1L, 101L, null, null);
            });

            assertTrue(exception.getMessage().contains("Book deletion cancelled"));
            assertTrue(exception.getMessage().contains("101 - Spring Boot"));
            assertTrue(exception.getMessage().contains("102 - Hibernate ORM"));
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        }
    }

