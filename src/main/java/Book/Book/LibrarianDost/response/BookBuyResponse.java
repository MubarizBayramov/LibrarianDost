package Book.Book.LibrarianDost.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBuyResponse {
    private String message;
    private String bookName;
    private int quantity;

}
