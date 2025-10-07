package Book.Book.LibrarianDost.service;

import com.common.dto.RefundRequest;
import com.common.dto.PaymentRequest;
import com.common.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentWebService implements PaymentService {

    private final RestTemplate webClient;

    @Override
    public PaymentResponse makePayment(PaymentRequest request) {
        ResponseEntity<PaymentResponse> response = webClient.postForEntity(
                "http://localhost:8880/payments",
                request,
                PaymentResponse.class
        );
        return response.getBody();
    }

    @Override
    public PaymentResponse refundPayment(String transactionCode, double amount) {
        RefundRequest refundRequest = new RefundRequest(transactionCode, amount);
        ResponseEntity<PaymentResponse> response = webClient.postForEntity(
                "http://localhost:8880/payments/refund/" + transactionCode,
                refundRequest,
                PaymentResponse.class
        );
        return response.getBody();
    }



    @Override
    public List<PaymentResponse> getAllPayments(double amount) {
        return List.of();
    }
}
