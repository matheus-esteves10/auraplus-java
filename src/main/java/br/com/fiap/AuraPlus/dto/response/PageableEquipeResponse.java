package br.com.fiap.AuraPlus.dto.response;

import org.springframework.data.domain.Page;

public record PageableEquipeResponse(
        String nomeTime,
        String descricao,
        Page<UsuarioEquipeResponse> usuarios

) {
}
