package Book.Book.LibrarianDost.service;

import Book.Book.LibrarianDost.request.PaymentRequest;
import Book.Book.LibrarianDost.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class PaymentEventService implements PaymentService {

    private final JmsTemplate jmsTemplate;

    @Override
    public PaymentResponse pay(PaymentRequest request) {
        jmsTemplate.convertAndSend("payment-queue", request);
        return new PaymentResponse("SENT", "OK", "Payment request sent via MQ");
    }

    @Override
    public PaymentResponse refundBook(String transactionCode, double amount) {
        jmsTemplate.convertAndSend("refund-queue", new RefundMessage(transactionCode, amount));
        return new PaymentResponse("REFUNDED", "OK", "Payment request sent via MQ");
    }
}
