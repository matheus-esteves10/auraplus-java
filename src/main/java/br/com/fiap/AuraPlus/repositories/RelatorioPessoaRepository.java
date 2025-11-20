package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.RelatorioEquipe;
import br.com.fiap.AuraPlus.model.RelatorioPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RelatorioPessoaRepository extends JpaRepository<RelatorioPessoa, Long> {

    @Query("SELECT r FROM RelatorioPessoa r " +
            "WHERE r.usuario.id = :usuarioId " +
            "AND MONTH(r.data) = :mes " +
            "AND YEAR(r.data) = :ano")
    Optional<RelatorioPessoa> findByUserAndMes(
            @Param("usuarioId") Long usuarioId,
            @Param("mes") int mes,
            @Param("ano") int ano
    );
}
