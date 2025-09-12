package Book.Book.LibrarianDost.request;



public class PaymentRequest {

    private Double price;
    private String client;      // hansı layihədən gəlir
    private String buyerId;     // alıcı ID
    private String sellerId;    // satıcı ID

    // Default constructor
    public PaymentRequest() {
    }

    // Parametrli constructor
    public PaymentRequest(Double price, String client, String buyerId, String sellerId) {
        this.price = price;
        this.client = client;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }

    // Getters və Setters
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}

