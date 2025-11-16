package br.com.fiap.AuraPlus.dto.response;

import br.com.fiap.AuraPlus.model.Usuario;

public record UsuarioEquipeResponse(
        Long idUser,
        String email,
        String cargo
) {

    public static UsuarioEquipeResponse from(Usuario u) {
        return new UsuarioEquipeResponse(
                u.getId(),
                u.getEmail(),
                u.getCargo()
        );
    }
}
