package Book.Book.LibrarianDost.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private Double amount;

    @NotBlank(message = "Client name is required")
    private String client; // Hangi layihədən gəlir, məsələn: LIBRARIAN

    @NotBlank(message = "Operation type is required")
    private String operationType; // PAYMENT / REFUND

    @NotBlank(message = "Buyer ID is required")
    private String buyerId;

    private String sellerId; // Optional, əgər satıcı varsa
}
