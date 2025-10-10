package Book.Book.LibrarianDost.service;

import com.common.dto.PaymentRequest;
import com.common.dto.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PaymentWebServiceTest {

    @Mock
    private RestTemplate webClient;

    @InjectMocks
    private PaymentWebService paymentWebService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMakePayment_ReturnsPaymentResponse() {

        PaymentRequest request = new PaymentRequest();
        request.setAmount(150.0);
        request.setClient("Mubariz");
        request.setTransactionCode("TX98765");





        PaymentResponse mockResponse = new PaymentResponse();
        mockResponse.setStatus("SUCCESS");
        mockResponse.setTransactionId("TX98765");


        when(webClient.postForEntity(
                eq("http://localhost:8880/payments"),
                eq(request),
                eq(PaymentResponse.class)
        )).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));


        PaymentResponse response = paymentWebService.makePayment(request);

        // Nəticəni yoxlayırıq
        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("TX98765", response.getTransactionCode());

        // RestTemplate çağırıldığını yoxlayırıq
        verify(webClient, times(1)).postForEntity(
                eq("http://localhost:8880/payments"),
                eq(request),
                eq(PaymentResponse.class)
        );
    }




    @Test
    void testGetAllPayments_ShouldReturnEmptyList() {

        double amount = 100.0;

        List<PaymentResponse> responses = paymentWebService.getAllPayments(amount);


        assertNotNull(responses);
        assertTrue(responses.isEmpty(), "List boş olmalıdır, çünki metod List.of() qaytarır");
    }
}
