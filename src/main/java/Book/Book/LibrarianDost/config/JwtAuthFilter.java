package Book.Book.LibrarianDost.config;

import Book.Book.LibrarianDost.repository.SellerRepository;
import Book.Book.LibrarianDost.repository.BuyerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        String username = jwtService.extractUsername(token);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            var seller = sellerRepository.findByName(username);
            if (seller.isPresent()) {
                User user = new User(username, seller.get().getPassword(), Collections.emptyList());
                setAuthentication(user, request);
            } else {
                var buyer = buyerRepository.findByName(username);
                if (buyer.isPresent()) {
                    User user = new User(username, buyer.get().getPassword(), Collections.emptyList());
                    setAuthentication(user, request);
                }
            }
        }

        chain.doFilter(request, response);
    }

    private void setAuthentication(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
