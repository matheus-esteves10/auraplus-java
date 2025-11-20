package br.com.fiap.AuraPlus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_ARP_RELATORIO_EQUIPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioEquipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime data = LocalDateTime.now();

    @Column(name = "sentimento_medio", length = 100)
    private String sentimentoMedio;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descritivo;

    @ManyToOne
    @JoinColumn(name = "id_equipe", nullable = false)
    private Equipe equipe;
}
