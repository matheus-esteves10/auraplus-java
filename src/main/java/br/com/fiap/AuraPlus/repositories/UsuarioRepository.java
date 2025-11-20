package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(final String username);

    Page<Usuario> findByEquipeId(Long equipeId, Pageable pageable);

    List<Usuario> findByEquipeId(Long equipeId);

    List<Usuario> findAllByEmailIn(Set<String> emails);
}
