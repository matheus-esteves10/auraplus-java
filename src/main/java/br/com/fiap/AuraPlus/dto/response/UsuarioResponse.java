package br.com.fiap.AuraPlus.dto.response;

import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.model.enums.Role;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Role role,
        Boolean ativo,
        String cargo
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getAtivo(),
                usuario.getCargo()
        );
    }
}
