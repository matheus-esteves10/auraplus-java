package br.com.fiap.AuraPlus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "T_ARP_EQUIPE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_time", nullable = false, length = 100)
    private String nomeTime;

    @Column(length = 255)
    private String descricao;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelatorioEquipe> relatoriosEquipe;
}
