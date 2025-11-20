package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.RelatorioPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    @Query("""
    SELECT MAX(r.numeroIndicacoes)
    FROM RelatorioPessoa r
    WHERE MONTH(r.data) = :mes
      AND YEAR(r.data) = :ano
      AND r.usuario.equipe.id = :equipeId
""")
    Integer findMaxIndicacoesByMesAnoAndEquipe(
            @Param("mes") int mes,
            @Param("ano") int ano,
            @Param("equipeId") Long equipeId
    );

    @Query("""
    SELECT r
    FROM RelatorioPessoa r
    WHERE MONTH(r.data) = :mes
      AND YEAR(r.data) = :ano
      AND r.usuario.equipe.id = :equipeId
      AND r.numeroIndicacoes = :maxIndicacoes
""")
    List<RelatorioPessoa> findAllByMesAnoEquipeAndIndicacoes(
            @Param("mes") int mes,
            @Param("ano") int ano,
            @Param("equipeId") Long equipeId,
            @Param("maxIndicacoes") int maxIndicacoes
    );
}
