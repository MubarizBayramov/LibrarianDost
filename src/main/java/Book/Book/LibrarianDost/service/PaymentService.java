package Book.Book.LibrarianDost.service;

import com.common.dto.PaymentRequest;
import com.common.dto.PaymentResponse;


public interface PaymentService {
    PaymentResponse pay(PaymentRequest request);
    PaymentResponse refundBook(String transactionCode, double amount);
}
