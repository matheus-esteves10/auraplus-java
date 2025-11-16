package br.com.fiap.AuraPlus.dto.response;

import java.util.Set;

public record CadastrarNaEquipeResponse(
        Set<UsuarioEquipeResponse> adicionados,
        Set<String> naoEncontrados,
        Set<String> jaEmOutroTime,
        Set<String> jaNaEquipe
) {
}
