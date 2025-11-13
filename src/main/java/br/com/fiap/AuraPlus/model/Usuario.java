package br.com.fiap.AuraPlus.model;

import br.com.fiap.AuraPlus.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_arp_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 50)
    private Role role;

    @Column(length = 100)
    private String cargo;

    @Column(name = "data_admissao")
    private LocalDateTime dataAdmissao = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "id_equipe")
    private Equipe equipe;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sentimento> sentimentos;

    @OneToMany(mappedBy = "reconhecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reconhecimento> reconhecimentosFeitos;

    @OneToMany(mappedBy = "reconhecido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reconhecimento> reconhecimentosRecebidos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelatorioPessoa> relatoriosPessoa;
}