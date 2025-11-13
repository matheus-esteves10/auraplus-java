package br.com.fiap.AuraPlus.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "C")
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
}
