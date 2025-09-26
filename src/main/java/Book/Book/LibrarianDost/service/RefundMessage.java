package Book.Book.LibrarianDost.service;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Data

public class RefundMessage implements Serializable {
    private String transactionCode;
    private double amount;

    public RefundMessage() {}

    public RefundMessage(String transactionCode, double amount) {
        this.transactionCode = transactionCode;
        this.amount = amount;
    }

}