package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.Sentimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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
}
