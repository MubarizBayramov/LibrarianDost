package Book.Book.LibrarianDost.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookAddRequest {

    @NotBlank(message = "Book name cannot be blank")
    private String name;

    @NotBlank(message = "Author name cannot be blank")
    private String author;


    @NotNull(message = "amount cannot be null")
    @Min(value = 1, message = "amount must be at least 1")
    private Double amount;


    @NotNull(message = "Stock cannot be null")
    @Min(value = 1, message = "Stock must be at least 1")
    private Integer stock;
}
