package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(final String username);
}
