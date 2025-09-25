package Book.Book.LibrarianDost.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookUpdateRequest {

    @NotBlank(message = "Book name is required")
    private String name;

    @NotBlank(message = "Author is required")
    private String author;

    @NotNull(message = "amount is required")
    private Double amount;

    private Integer stock;
}
