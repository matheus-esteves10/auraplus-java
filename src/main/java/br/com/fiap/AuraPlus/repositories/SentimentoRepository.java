package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.Sentimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SentimentoRepository extends JpaRepository<Sentimento, Long> {

    @Query("""
       SELECT COUNT(s) > 0
       FROM Sentimento s
       WHERE s.usuario.id = :userId
           AND FUNCTION('TO_CHAR', s.data, 'YYYY-MM-DD') = FUNCTION('TO_CHAR', CURRENT_DATE, 'YYYY-MM-DD')
       """)
    boolean existsSentimentoDeHoje(@Param("userId") Long userId);


    @Query("""
       SELECT s FROM Sentimento s
       WHERE s.usuario.id = :usuarioId
         AND FUNCTION('TO_CHAR', s.data, 'YYYY-MM-DD') = FUNCTION('TO_CHAR', CURRENT_DATE, 'YYYY-MM-DD')
       """)
    Optional<Sentimento> findSentimentoDeHoje(@Param("usuarioId") Long usuarioId);
}
