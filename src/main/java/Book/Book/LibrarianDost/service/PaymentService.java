package Book.Book.LibrarianDost.service;

import com.common.dto.PaymentRequest;
import com.common.dto.PaymentResponse;

import java.util.List;


public interface PaymentService {
    PaymentResponse pay(PaymentRequest request);
    PaymentResponse refundBook(String transactionCode, double amount);
    List<PaymentResponse> getAllPayments(double amount);
}
