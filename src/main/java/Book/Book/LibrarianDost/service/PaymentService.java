package Book.Book.LibrarianDost.service;

import Book.Book.LibrarianDost.request.PaymentRequest;
import Book.Book.LibrarianDost.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse pay(PaymentRequest request);
    public PaymentResponse refundBook(String transactionCode, double amount);
}
