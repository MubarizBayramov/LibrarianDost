package Book.Book.LibrarianDost.service;

import Book.Book.LibrarianDost.config.JwtService;
import Book.Book.LibrarianDost.entity.Buyer;
import Book.Book.LibrarianDost.entity.Seller;
import Book.Book.LibrarianDost.repository.BuyerRepository;
import Book.Book.LibrarianDost.repository.SellerRepository;
import Book.Book.LibrarianDost.request.BuyerLoginRequest;
import Book.Book.LibrarianDost.request.BuyerRegisterRequest;
import Book.Book.LibrarianDost.request.SellerRegisterRequest;
import Book.Book.LibrarianDost.request.SellerLoginRequest;
import Book.Book.LibrarianDost.response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(SellerRegisterRequest request) {
        Seller seller = new Seller();
        seller.setName(request.getName());
        seller.setPhone(request.getPhone());
        seller.setPassword(passwordEncoder.encode(request.getPassword()));
        sellerRepository.save(seller);
        return "Seller registered successfully!";
    }

    public JwtResponse login(SellerLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
        );
        var seller = sellerRepository.findByName(request.getName())
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        String token = jwtService.generateToken(seller.getName());
        return new JwtResponse(token);
    }

    public String register(BuyerRegisterRequest request) {
        Buyer buyer = new Buyer();
        buyer.setName(request.getName());
        buyer.setPhone(request.getPhone());
        buyer.setPassword(passwordEncoder.encode(request.getPassword()));
        buyerRepository.save(buyer);
        return "Buyer registered successfully!";
    }

    public JwtResponse login(BuyerLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
        );
        var buyer = buyerRepository.findByName(request.getName())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        String token = jwtService.generateToken(buyer.getName());
        return new JwtResponse(token);
    }

}
