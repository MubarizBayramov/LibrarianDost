package Book.Book.LibrarianDost.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor


public class RefundRequest implements Serializable {
        private double amount;

}
