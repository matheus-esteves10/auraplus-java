package br.com.fiap.AuraPlus.service.auth;

import br.com.fiap.AuraPlus.exceptions.UsuarioNotFoundException;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.repositories.UsuarioRepository;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    private final Algorithm algorithm;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public TokenService(
            @Value("${jwt.secret}") String secret,
            UsuarioRepository usuarioRepository
    ) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("jwt.secret não pode ser nulo ou vazio");
        }

        this.algorithm = Algorithm.HMAC256(secret);
        this.usuarioRepository = usuarioRepository;
    }

        public String createToken(final Usuario user) {
        Instant expiresAt = LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.ofHours(-3));

        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("nome", user.getNome())
                .withClaim("role", user.getRole().toString())
                .withClaim("email", user.getEmail())
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
    }

    public Usuario getUserFromToken(final String jwt) {
        var jwtVerified = JWT.require(algorithm).build().verify(jwt);
        Long userId = Long.valueOf(jwtVerified.getSubject());

        return usuarioRepository.findById(userId)
                .orElseThrow(UsuarioNotFoundException::new);
    }


}
