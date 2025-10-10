package Book.Book.LibrarianDost.service;


import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.entity.Seller;
import Book.Book.LibrarianDost.repository.BookRepository;
import Book.Book.LibrarianDost.response.BookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BuyerServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BuyerService buyerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks_ReturnsMappedBookResponses() {

        Seller seller = new Seller();
        seller.setId(10L);

        Book book1 = new Book();
        book1.setId(1L);
        book1.setName("Java Basics");
        book1.setAuthor("Rahid");
        book1.setAmount(5.0);
        book1.setSeller(seller);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setName("Spring Boot");
        book2.setAuthor("Mübariz");
        book2.setAmount(3.0);
        book2.setSeller(null);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<BookResponse> responses = buyerService.getAllBooks();


        assertEquals(2, responses.size());
        assertEquals(book1.getName(), responses.get(0).getName());
        assertEquals(book1.getSeller().getId(), responses.get(0).getSellerId());
        assertNull(responses.get(1).getSellerId());
        verify(bookRepository, times(1)).findAll();
    }


        @Test
        void testSearchBooks_ByNameAndSeller() {
            Seller seller = new Seller();
            seller.setId(1L);

            Book book1 = new Book();
            book1.setId(101L);
            book1.setName("Java Basics");
            book1.setAuthor("Rahid");
            book1.setAmount(5.0);
            book1.setSeller(seller);

            Book book2 = new Book();
            book2.setId(102L);
            book2.setName("Spring Boot");
            book2.setAuthor("Mübariz");
            book2.setAmount(7.0);
            book2.setSeller(seller);

            when(bookRepository.findByNameContainingIgnoreCaseAndSeller_NameContainingIgnoreCase("Java", "Rahid"))
                    .thenReturn(Arrays.asList(book1));

            List<BookResponse> result = buyerService.searchBooks("Java", null, null, "Rahid");

            assertEquals(1, result.size());
            assertEquals("Java Basics", result.get(0).getName());
            assertEquals(1L, result.get(0).getSellerId());
        }

        @Test
        void testSearchBooks_BySellerOnly() {
            Seller seller = new Seller();
            seller.setId(2L);

            Book book1 = new Book();
            book1.setId(103L);
            book1.setName("Hibernate ORM");
            book1.setAuthor("Ali");
            book1.setAmount(6.0);
            book1.setSeller(seller);

            when(bookRepository.findBySeller_NameContainingIgnoreCase("Ali"))
                    .thenReturn(Arrays.asList(book1));

            List<BookResponse> result = buyerService.searchBooks(null, null, null, "Ali");

            assertEquals(1, result.size());
            assertEquals("Hibernate ORM", result.get(0).getName());
            assertEquals(2L, result.get(0).getSellerId());
        }

        @Test
        void testSearchBooks_NoFilters() {
            Book book1 = new Book();
            book1.setId(104L);
            book1.setName("Python Basics");
            book1.setAuthor("Leyla");
            book1.setAmount(4.0);
            book1.setSeller(null);

            Book book2 = new Book();
            book2.setId(105L);
            book2.setName("C++ Basics");
            book2.setAuthor("Orkhan");
            book2.setAmount(5.0);
            book2.setSeller(null);

            when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

            List<BookResponse> result = buyerService.searchBooks(null, null, null, null);

            assertEquals(2, result.size());
            assertNull(result.get(0).getSellerId());
            assertNull(result.get(1).getSellerId());
        }
    }



