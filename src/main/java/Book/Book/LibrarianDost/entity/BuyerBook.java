package Book.Book.LibrarianDost.entity;

import jakarta.persistence.*;

@Entity
public class BuyerBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Buyer buyer;

    @ManyToOne
    private Book book;

    private int quantity;

    private String transactionCode;

    public BuyerBook() {}

    public BuyerBook(Long id, Buyer buyer, Book book, int quantity, String transactionCode) {
        this.id = id;
        this.buyer = buyer;
        this.book = book;
        this.quantity = quantity;
        this.transactionCode = transactionCode;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Buyer getBuyer() { return buyer; }
    public void setBuyer(Buyer buyer) { this.buyer = buyer; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
}
