package Book.Book.LibrarianDost.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Data
public class BookException extends RuntimeException {

    private final HttpStatus status;


    public BookException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }


    public BookException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
