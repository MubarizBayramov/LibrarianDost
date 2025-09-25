package Book.Book.LibrarianDost.service;

import Book.Book.LibrarianDost.request.PaymentRequest;
import Book.Book.LibrarianDost.response.PaymentResponse;
import org.springframework.context.annotation.Primary;

@Primary
public class PaymentEventService implements PaymentService {
    @Override
    public PaymentResponse pay(PaymentRequest request) {
        return null;
    }
}
