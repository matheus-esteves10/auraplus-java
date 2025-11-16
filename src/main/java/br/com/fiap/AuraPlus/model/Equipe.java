package br.com.fiap.AuraPlus.model;

import br.com.fiap.AuraPlus.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Builder.Default
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelatorioEquipe> relatoriosEquipe;

    public void adicionarUsuario(final Usuario usuario) {

        if (usuarios.isEmpty()) {
            usuario.setRole(Role.ADMIN);
        } else {
            usuario.setRole(Role.COLABORADOR);
        }
        usuario.setEquipe(this);
        usuario.setDataAdmissao(LocalDateTime.now());

        this.usuarios.add(usuario);
    }

    public void removerUsuario(final Usuario usuario) {
        usuario.setEquipe(null);
        usuario.setRole(Role.NOVO_USUARIO);
        usuario.setDataAdmissao(null);
        usuario.setCargo(null);
        this.usuarios.remove(usuario);
    }
}
