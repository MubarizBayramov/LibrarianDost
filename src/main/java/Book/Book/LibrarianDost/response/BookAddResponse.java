package Book.Book.LibrarianDost.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAddResponse {
    private String name;
    private String author;
    private double price;
    private int stock;
}
