package br.com.fiap.AuraPlus.dto.response;

import br.com.fiap.AuraPlus.model.Equipe;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.model.enums.Role;

import java.util.Optional;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Role role,
        Boolean ativo,
        String cargo,
        Long equipeId,
        String equipeNome
) {
    public static UsuarioResponse from(Usuario usuario) {

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getAtivo(),
                usuario.getCargo(),
                Optional.ofNullable(usuario.getEquipe()).map(Equipe::getId).orElse(null),
                Optional.ofNullable(usuario.getEquipe()).map(Equipe::getNomeTime).orElse(null)
        );
    }
}
