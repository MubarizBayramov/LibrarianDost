package Book.Book.LibrarianDost.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBuyResponse {

    private String message;   // əməliyyat haqqında mesaj
    private String bookName;  // kitabın adı
    private Integer quantity; // alınan kitabların sayı
    private String marker;    // "-" işarəsi
}
