package br.com.fiap.AuraPlus.controller;

import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.service.auth.AuthService;
import br.com.fiap.AuraPlus.service.auth.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    public record Credentials(String username, String password) {}

    public record TokenResponse(String token) {}

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Autenticar usuario", description = "Retorna um token JWT válido se as credenciais estiverem corretas")
    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid Credentials credentials) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.username(),
                        credentials.password()
                )
        );

        Usuario usuario = (Usuario) auth.getPrincipal();
        String token = tokenService.createToken(usuario);

        return ResponseEntity.ok(new TokenResponse(token));
    }
}
