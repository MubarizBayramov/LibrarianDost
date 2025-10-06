package Book.Book.LibrarianDost.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private Double balance = 1000.0;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BuyerBook> buyerBooks = new ArrayList<>();

    public Buyer() {}

    public Buyer(Long id, String name, String phone, String email, Double balance, List<BuyerBook> buyerBooks) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.balance = balance;
        this.buyerBooks = buyerBooks;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }

    public List<BuyerBook> getBuyerBooks() { return buyerBooks; }
    public void setBuyerBooks(List<BuyerBook> buyerBooks) { this.buyerBooks = buyerBooks; }
}
