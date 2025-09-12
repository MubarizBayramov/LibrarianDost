package Book.Book.LibrarianDost.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private Double price;
    private String clientCode;
    private String transactionCode;


}
