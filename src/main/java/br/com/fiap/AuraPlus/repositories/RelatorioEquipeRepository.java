package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.RelatorioEquipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RelatorioEquipeRepository extends JpaRepository<RelatorioEquipe, Long> {

    @Query("SELECT r FROM RelatorioEquipe r " +
            "WHERE r.equipe.id = :equipeId " +
            "AND MONTH(r.data) = :mes " +
            "AND YEAR(r.data) = :ano")
    Optional<RelatorioEquipe> findByEquipeAndMes(
            @Param("equipeId") Long equipeId,
            @Param("mes") int mes,
            @Param("ano") int ano
    );

    @Query("SELECT COUNT(r) FROM RelatorioEquipe r " +
            "WHERE r.equipe.id = :equipeId " +
            "AND MONTH(r.data) = :mes " +
            "AND YEAR(r.data) = :ano")
    Integer countReportsByEquipeAndMes(
            @Param("equipeId") Long equipeId,
            @Param("mes") int mes,
            @Param("ano") int ano
    );
}
