package Book.Book.LibrarianDost.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundMessage {
    private String transactionCode;
    private Double amount;
}
