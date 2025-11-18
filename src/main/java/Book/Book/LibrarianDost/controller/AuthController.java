package Book.Book.LibrarianDost.controller;

import Book.Book.LibrarianDost.request.BuyerLoginRequest;
import Book.Book.LibrarianDost.request.BuyerRegisterRequest;
import Book.Book.LibrarianDost.request.SellerRegisterRequest;
import Book.Book.LibrarianDost.request.SellerLoginRequest;
import Book.Book.LibrarianDost.response.JwtResponse;
import Book.Book.LibrarianDost.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register/seller")
    public String register(@RequestBody SellerRegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login/seller")
    public JwtResponse login(@RequestBody SellerLoginRequest request) {
        return authService.login(request);
    }


    @PostMapping("/register/buyer")
    public String register(@RequestBody BuyerRegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login/buyer")
    public JwtResponse login(@RequestBody BuyerLoginRequest request) {
        return authService.login(request);
    }

}
