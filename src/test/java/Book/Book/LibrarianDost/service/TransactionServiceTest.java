package Book.Book.LibrarianDost.service;

import Book.Book.LibrarianDost.entity.Book;
import Book.Book.LibrarianDost.entity.Buyer;
import Book.Book.LibrarianDost.entity.Seller;
import Book.Book.LibrarianDost.exception.MyException;
import Book.Book.LibrarianDost.repository.BookRepository;
import Book.Book.LibrarianDost.repository.BuyerBookRepository;
import Book.Book.LibrarianDost.repository.BuyerRepository;
import Book.Book.LibrarianDost.repository.SellerRepository;
import Book.Book.LibrarianDost.response.BookBuyResponse;
import com.common.dto.PaymentRequest;
import com.common.dto.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BuyerBookRepository buyerBookRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private TransactionService transactionService;

    private Buyer buyer;
    private Book book;
    private Seller seller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        buyer = new Buyer();
        buyer.setId(1L);
        buyer.setName("Mubariz");

        seller = new Seller();
        seller.setId(10L);
        seller.setName("Rahid");

        book = new Book();
        book.setId(100L);
        book.setName("Spring Boot Guide");
        book.setStock(10);
        book.setAmount(250.0);
        book.setSeller(seller);
    }

    @Test
    void testBuyBooks_Success() {

        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        when(bookRepository.findById(100L)).thenReturn(Optional.of(book));


        PaymentResponse paymentResponse = new PaymentResponse(
                "TXN12345",
                "SUCCESS",
                buyer.getName(),
                book.getAmount()
        );
        when(paymentService.makePayment(any(PaymentRequest.class))).thenReturn(paymentResponse);


        List<BookBuyResponse> responses = transactionService.buyBooks(1L, List.of(100L));


        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Spring Boot Guide", responses.get(0).getBookName());

        verify(buyerRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findById(100L);
        verify(paymentService, times(1)).makePayment(any(PaymentRequest.class));
    }

    @Test
    void testBuyBooks_BuyerNotFound() {
        when(buyerRepository.findById(999L)).thenReturn(Optional.empty());

        MyException ex = assertThrows(MyException.class,
                () -> transactionService.buyBooks(999L, List.of(1L)));

        assertEquals("Buyer not found", ex.getMessage());
        verify(buyerRepository, times(1)).findById(999L);
    }
}
