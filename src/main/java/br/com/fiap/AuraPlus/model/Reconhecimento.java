package br.com.fiap.AuraPlus.model;

import br.com.fiap.AuraPlus.exceptions.CannotAutoRecognitionException;
import br.com.fiap.AuraPlus.exceptions.NotFromTeamException;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_ARP_RECONHECIMENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reconhecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 255)
    private String descricao;

    @Column
    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_reconhecedor", nullable = false)
    private Usuario reconhecedor;

    @ManyToOne
    @JoinColumn(name = "id_reconhecido", nullable = false)
    private Usuario reconhecido;

    public static Reconhecimento cadastrarReconhecimento(final String titulo, final String descricao) {
        return Reconhecimento.builder()
                .titulo(titulo)
                .descricao(descricao)
                .data(LocalDateTime.now())
                .build();
    }

    public static void validacoesReconhecimento(final Usuario reconhecedor, final Usuario reconhecido) {
        assert reconhecedor != null;
        assert reconhecido != null;

        if (reconhecedor.getId().equals(reconhecido.getId())) {
            throw new CannotAutoRecognitionException();
        }


        if (reconhecedor.getEquipe() == null || reconhecido.getEquipe() == null ||
                !reconhecedor.getEquipe().getId().equals(reconhecido.getEquipe().getId())) {
            throw new NotFromTeamException(reconhecedor.getEmail(), reconhecido.getEmail());
        }
    }
}
