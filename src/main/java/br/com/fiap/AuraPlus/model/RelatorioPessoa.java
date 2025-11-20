package br.com.fiap.AuraPlus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_ARP_RELATORIO_PESSOA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_indicacoes")
    private int numeroIndicacoes = 0;

    @Column
    private LocalDateTime data = LocalDateTime.now();

    @Lob
    @Column(columnDefinition = "TEXT")
    private String descritivo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
