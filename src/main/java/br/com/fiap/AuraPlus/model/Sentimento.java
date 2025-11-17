package br.com.fiap.AuraPlus.model;

import br.com.fiap.AuraPlus.model.enums.TipoSentimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_ARP_SENTIMENTOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sentimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_sentimento", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private TipoSentimento nomeSentimento;

    @Column(name = "valor_pontuacao")
    private int valorPontuacao;

    @Column
    private LocalDateTime data = LocalDateTime.now();

    @Column(length = 255)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    private static int calcularPontuacao(final TipoSentimento tipo) {
        return switch (tipo) {
            case MUITO_TRISTE -> 1;
            case TRISTE -> 2;
            case NEUTRO -> 3;
            case FELIZ -> 4;
            case MUITO_FELIZ -> 5;
        };
    }

    public static Sentimento of(final TipoSentimento tipo, final String descricao) {
        return Sentimento.builder()
                .nomeSentimento(tipo)
                .descricao(descricao)
                .valorPontuacao(calcularPontuacao(tipo))
                .data(LocalDateTime.now())
                .build();
    }
}
