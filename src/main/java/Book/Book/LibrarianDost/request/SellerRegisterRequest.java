package Book.Book.LibrarianDost.request;

import lombok.Data;

@Data
public class SellerRegisterRequest {
    private String name;
    private String phone;
    private String password;
}
