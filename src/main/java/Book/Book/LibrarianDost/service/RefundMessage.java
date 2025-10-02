package Book.Book.LibrarianDost.service;


import java.io.Serializable;

public class RefundMessage implements Serializable {
    private String transactionCode;
    private Double amount;


    public RefundMessage() {}


    public RefundMessage(String transactionCode, Double amount) {
        this.transactionCode = transactionCode;
        this.amount = amount;
    }


    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
