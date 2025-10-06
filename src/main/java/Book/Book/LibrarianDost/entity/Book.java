package Book.Book.LibrarianDost.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String author;

    @NotNull(message = "amount cannot be null")
    @Min(value = 1, message = "amount must be at least 1")
    @Column(nullable = false)
    private Double amount;

    private Integer stock;
    private String marker;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonBackReference
    private Seller seller;

    // Parametrsiz konstruktor
    public Book() {}

    // Bütün sahələri qəbul edən konstruktor
    public Book(Long id, String name, String author, Double amount, Integer stock, String marker, Seller seller) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.amount = amount;
        this.stock = stock;
        this.marker = marker;
        this.seller = seller;
    }

    // Getter və Setter-lər
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getMarker() { return marker; }
    public void setMarker(String marker) { this.marker = marker; }

    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }
}
