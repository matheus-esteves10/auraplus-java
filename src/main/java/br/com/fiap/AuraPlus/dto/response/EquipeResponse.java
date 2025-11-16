package br.com.fiap.AuraPlus.dto.response;

import br.com.fiap.AuraPlus.model.Equipe;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EquipeResponse(@NotNull String nomeTime,
                             String descricao,
                             List<UsuarioEquipeResponse> usuarios) {

    public static EquipeResponse from(Equipe equipe) {
        return new EquipeResponse(
                equipe.getNomeTime(),
                equipe.getDescricao(),
                equipe.getUsuarios().stream()
                        .map(UsuarioEquipeResponse::from)
                        .toList()
        );
    }
}
