package Book.Book.LibrarianDost.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.badRequest().body(response);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MyException.class)
    public ResponseEntity<Map<String, String>> handleMyException(MyException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOtherExceptions(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal server error: " + e.getMessage());
        return ResponseEntity.status(500).body(response);
    }
}
