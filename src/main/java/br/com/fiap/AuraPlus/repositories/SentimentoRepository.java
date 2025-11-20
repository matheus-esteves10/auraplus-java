package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.Sentimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SentimentoRepository extends JpaRepository<Sentimento, Long> {

    @Query("""
    SELECT s FROM Sentimento s
    WHERE s.usuario.id = :userId
      AND s.data >= :inicio
      AND s.data < :fim
""")
    Optional<Sentimento> findSentimentoDeHoje(@Param("userId") Long userId,
                                              @Param("inicio") LocalDateTime inicio,
                                              @Param("fim") LocalDateTime fim);

    @Query("""
    SELECT s
    FROM Sentimento s
    JOIN s.usuario u
    WHERE u.equipe.id = :equipeId
      AND s.data >= :inicioMes
      AND s.data < :fimMes
""")
    List<Sentimento> sentimentosDoMesPorEquipe(
            @Param("equipeId") Long equipeId,
            @Param("inicioMes") LocalDateTime inicioMes,
            @Param("fimMes") LocalDateTime fimMes
    );
}
