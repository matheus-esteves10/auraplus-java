package br.com.fiap.AuraPlus.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CadastroReconhecimentoDto(@NotBlank String titulo,
                                        String descricao) {
}
