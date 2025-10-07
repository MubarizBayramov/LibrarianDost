package Book.Book.LibrarianDost.service;

import com.common.dto.PaymentRequest;
import com.common.dto.PaymentResponse;

import java.util.List;


public interface PaymentService {
    PaymentResponse makePayment(PaymentRequest request);
    PaymentResponse refundPayment(String transactionCode, double amount);
    List<PaymentResponse> getAllPayments(double amount);
}
