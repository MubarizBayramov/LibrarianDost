package Book.Book.LibrarianDost.response;

import lombok.*;

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
