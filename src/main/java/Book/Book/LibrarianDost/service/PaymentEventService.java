/* package Book.Book.LibrarianDost.service;

import com.common.dto.PaymentRequest;
import com.common.dto.PaymentResponse;
import com.common.dto.RefundMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class PaymentEventService implements PaymentService {

    private final JmsTemplate jmsTemplate;

    @Override
    public PaymentResponse pay(PaymentRequest request) {
        jmsTemplate.convertAndSend("payment-queue", request);
        return new PaymentResponse(
                "SENT",
                "OK",
                request.getClient(),
                request.getAmount()
        );
    }

    @Override
    public PaymentResponse refundBook(String transactionCode, double amount) {
        jmsTemplate.convertAndSend("refund-queue", new RefundMessage(transactionCode, amount));
        return new PaymentResponse(
                transactionCode,
                "REFUNDED",
                "",
                amount
        );
    }

    @Override
    public List<PaymentResponse> getAllPayments(double amount) {
        jmsTemplate.convertAndSend("getAll-queue", new RefundMessage("N/A", amount));
        return new ArrayList<>();






 */