package Book.Book.LibrarianDost.response;

public class BookBuyResponse {

    private String message;   // əməliyyat haqqında mesaj
    private String bookName;  // kitabın adı
    private Integer quantity; // alınan kitabların sayı
    private String marker;    // "-" işarəsi

    // Parametrsiz konstruktor
    public BookBuyResponse() {
    }

    // Bütün sahələri qəbul edən konstruktor
    public BookBuyResponse(String message, String bookName, Integer quantity, String marker) {
        this.message = message;
        this.bookName = bookName;
        this.quantity = quantity;
        this.marker = marker;
    }

    // Getter və Setter-lər
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
}
