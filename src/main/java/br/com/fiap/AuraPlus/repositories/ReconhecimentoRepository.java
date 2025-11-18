package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.Reconhecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReconhecimentoRepository extends JpaRepository<Reconhecimento, Long> {

    @Query("""
    SELECT COUNT(r) > 0
    FROM Reconhecimento r
    WHERE r.reconhecedor.id = :idReconhecedor
      AND r.reconhecido.id = :idReconhecido
      AND r.data >= :inicio
      AND r.data < :fim
""")
    boolean existsReconhecimentoDoMes(
            @Param("idReconhecedor") Long idReconhecedor,
            @Param("idReconhecido") Long idReconhecido,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}
