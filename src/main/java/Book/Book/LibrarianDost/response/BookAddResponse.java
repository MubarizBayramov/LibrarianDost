package Book.Book.LibrarianDost.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAddResponse {
    private String name;
    private String author;
    private double amount;
    private int stock;
}
