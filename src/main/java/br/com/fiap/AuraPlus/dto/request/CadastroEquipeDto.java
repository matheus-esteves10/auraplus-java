package br.com.fiap.AuraPlus.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CadastroEquipeDto(@NotBlank String nomeEquipe,
                                String descricaoEquipe,
                                @NotBlank String cargoCriador
                                ) {
}
