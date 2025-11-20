package br.com.fiap.AuraPlus.repositories;

import br.com.fiap.AuraPlus.model.Reconhecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

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

    @Query("""
    SELECT COUNT(r)
    FROM Reconhecimento r
    WHERE r.reconhecido.id = :usuarioId
      AND r.data >= :inicioMes
      AND r.data < :fimMes
""")
    int countByUsuarioNoMes(Long usuarioId,
                            LocalDateTime inicioMes,
                            LocalDateTime fimMes);

    @Query("""
    SELECT STRING_AGG(r.descricao, ' || ')
    FROM Reconhecimento r
    WHERE r.reconhecido.id = :usuarioId
      AND r.data >= :inicioMes
      AND r.data < :fimMes
""")
    String descricoesDoMes(Long usuarioId,
                           LocalDateTime inicioMes,
                           LocalDateTime fimMes);

    @Query("""
    SELECT STRING_AGG(r.titulo, ' || ')
    FROM Reconhecimento r
    WHERE r.reconhecido.id = :usuarioId
      AND r.data >= :inicioMes
      AND r.data < :fimMes
""")
    String titulosDoMes(Long usuarioId,
                           LocalDateTime inicioMes,
                           LocalDateTime fimMes);

    @Query("""
    SELECT DISTINCT r.reconhecido.id
    FROM Reconhecimento r
    WHERE r.data >= :inicioMes
      AND r.data < :fimMes
""")
    List<Long> usuariosComIndicacaoNoMes(LocalDateTime inicioMes,
                                         LocalDateTime fimMes);
}
