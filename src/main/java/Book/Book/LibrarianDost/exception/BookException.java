package Book.Book.LibrarianDost.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BookException extends RuntimeException {

    private final HttpStatus status; // HTTP status kodu

    // Sadə mesajlı constructor, default status = BAD_REQUEST
    public BookException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    // Mesaj və HTTP status təyin etmək üçün constructor
    public BookException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
