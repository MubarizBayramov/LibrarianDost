package Book.Book.LibrarianDost.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor


public class PaymentRequest implements Serializable {
    private String client;
    private Double amount;
    private String operationType;
    private String buyerId;
    private String sellerId;

}

