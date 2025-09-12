package Book.Book.LibrarianDost.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookAddRequest {

    @NotBlank(message = "Book name cannot be blank") // boş ola bilməz
    private String name;

    @NotBlank(message = "Author name cannot be blank") // boş ola bilməz
    private String author;


    @NotNull(message = "Price cannot be null")
    @Min(value = 100, message = "Price must be at least 100")
    private Double price;


    @NotNull(message = "Stock cannot be null") // stock null ola bilməz
    @Min(value = 1, message = "Stock must be at least 1") // minimum 1
    private Integer stock;
}
