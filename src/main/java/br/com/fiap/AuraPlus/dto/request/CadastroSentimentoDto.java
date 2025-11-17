package br.com.fiap.AuraPlus.dto.request;

import br.com.fiap.AuraPlus.model.enums.TipoSentimento;
import jakarta.validation.constraints.NotNull;


public record CadastroSentimentoDto(@NotNull TipoSentimento tipo,
                                    String descricao) {
}
