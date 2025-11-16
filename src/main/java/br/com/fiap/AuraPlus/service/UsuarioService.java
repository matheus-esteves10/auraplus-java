package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.request.CadastroUserDto;
import br.com.fiap.AuraPlus.exceptions.UserWithoutTeamException;
import br.com.fiap.AuraPlus.exceptions.UsuarioNotFoundException;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.model.enums.Role;
import br.com.fiap.AuraPlus.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario salvarUsuario(final CadastroUserDto cadastroUserDto) {
        Usuario usuario = Usuario.builder()
                .nome(cadastroUserDto.nome())
                .email(cadastroUserDto.email())
                .senha(passwordEncoder.encode(cadastroUserDto.senha()))
                .role(Role.NOVO_USUARIO)
                .ativo(true)
                .build();
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizarUsuario(final CadastroUserDto cadastroUserDto, Usuario usuario) {
        usuario.setNome(cadastroUserDto.nome());
        usuario.setEmail(cadastroUserDto.email());
        usuario.setSenha(passwordEncoder.encode(cadastroUserDto.senha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario infosPessoaisUsuarioLogado(final String email) {
        return usuarioRepository.findByEmail(email).orElseThrow();
    }

    @Transactional
    public void excluirUsuario(final Usuario usuario) {
        usuario.setAtivo(false);
        usuario.setEquipe(null);
        usuario.setDataAdmissao(null);

        usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario alterarRole(final Long idUser) {
        final Usuario usuario = usuarioRepository.findById(idUser)
                .orElseThrow(UsuarioNotFoundException::new);

        if (usuario.getRole() == Role.NOVO_USUARIO) {
            throw new UserWithoutTeamException(usuario.getEmail());
        }

        usuario.setRole(
                usuario.getRole() == Role.GESTOR ? Role.COLABORADOR : Role.GESTOR
        );

        return usuarioRepository.save(usuario);
    }
}
