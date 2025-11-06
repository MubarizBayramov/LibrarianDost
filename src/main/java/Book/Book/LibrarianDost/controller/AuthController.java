package Book.Book.LibrarianDost.controller;

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

    @PostMapping("/register")
    public String register(@RequestBody SellerRegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody SellerLoginRequest request) {
        return authService.login(request);
    }
}
