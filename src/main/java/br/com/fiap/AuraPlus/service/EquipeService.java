package br.com.fiap.AuraPlus.service;

import br.com.fiap.AuraPlus.dto.request.CadastroEquipeDto;
import br.com.fiap.AuraPlus.dto.request.UsuarioEquipeRequest;
import br.com.fiap.AuraPlus.dto.response.CadastrarNaEquipeResponse;
import br.com.fiap.AuraPlus.dto.response.UsuarioEquipeResponse;
import br.com.fiap.AuraPlus.exceptions.EquipeNotFoundException;
import br.com.fiap.AuraPlus.exceptions.UserAlreadyInTeamException;
import br.com.fiap.AuraPlus.exceptions.UserWithoutTeamException;
import br.com.fiap.AuraPlus.exceptions.UsuarioNotFoundException;
import br.com.fiap.AuraPlus.model.Equipe;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.model.enums.Role;
import br.com.fiap.AuraPlus.repositories.EquipeRepository;
import br.com.fiap.AuraPlus.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final UsuarioRepository usuarioRepository;


    public EquipeService(EquipeRepository equipeRepository, UsuarioRepository usuarioRepository) {
        this.equipeRepository = equipeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Equipe cadastroEquipe(final CadastroEquipeDto equipeDto, final Usuario usuarioLogado) {

        final Usuario usuario = findUsuarioById(usuarioLogado.getId());

        if (usuario.getEquipe() != null) {
            throw new UserAlreadyInTeamException(usuario.getEmail());
        }

        final Equipe equipe = Equipe.builder()
                .nomeTime(equipeDto.nomeEquipe())
                .descricao(equipeDto.descricaoEquipe())
                .build();

        equipe.adicionarUsuario(usuario);
        usuario.setCargo(equipeDto.cargoCriador());

        return equipeRepository.save(equipe);
    }

    @Transactional
    public CadastrarNaEquipeResponse adicionarUsuarios(final Long equipeId, final Set<UsuarioEquipeRequest> usuariosReq) {

        final Equipe equipe = findEquipeById(equipeId);

        final Map<String, Usuario> usuariosEncontrados = carregarUsuariosPorEmail(usuariosReq);

        Set<String> naoEncontrados = new HashSet<>();
        Set<Usuario> adicionados = new HashSet<>();
        Set<String> jaEmOutroTime = new HashSet<>();
        Set<String> jaNaEquipe = new HashSet<>();

        processarUsuariosAdicionados(equipe, usuariosReq, usuariosEncontrados,
                adicionados, naoEncontrados, jaEmOutroTime, jaNaEquipe);

        usuarioRepository.saveAll(adicionados);

        final Set<UsuarioEquipeResponse> adicionadosResponse = adicionados.stream().map(this::toResponse).collect(Collectors.toSet());

        return new CadastrarNaEquipeResponse(
                adicionadosResponse,
                naoEncontrados,
                jaEmOutroTime,
                jaNaEquipe
        );
    }

    @Transactional
    public void removerUsuario(final Long idUserRemover, final Usuario usuarioLogado) {
        final Equipe equipe = findEquipeById(usuarioLogado.getEquipe().getId());
        final Usuario usuarioRemocao = usuarioRepository.findById(idUserRemover).orElseThrow(UsuarioNotFoundException::new);

        if (usuarioLogado.getRole() == Role.GESTOR
                && usuarioRemocao.getRole() == Role.ADMIN) {
            throw new AuthorizationDeniedException("Um GESTOR não pode remover um ADMIN da equipe.");
        }

        equipe.removerUsuario(usuarioRemocao);
        usuarioRepository.save(usuarioRemocao);

        //todo: enviar email notificando que foi removido da equipe
    }

    public Page<UsuarioEquipeResponse> listarUsuariosDaEquipe(final Usuario usuarioLogado, final Pageable pageable) {

        if (usuarioLogado.getEquipe() == null) {
            throw new UserWithoutTeamException(usuarioLogado.getEmail());
        }

        Page<Usuario> page = usuarioRepository.findByEquipeId(
                usuarioLogado.getEquipe().getId(),
                pageable
        );

        return page.map(this::toResponse);
    }

    // -------------------------
    // Métodos auxiliares
    // -------------------------

    private UsuarioEquipeResponse toResponse(Usuario usuario) {
        return new UsuarioEquipeResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getCargo());
    }

    private Map<String, Usuario> carregarUsuariosPorEmail(final Set<UsuarioEquipeRequest> usuariosReq) {
        Set<String> emailsReq = usuariosReq.stream()
                .map(UsuarioEquipeRequest::email)
                .collect(Collectors.toSet());

        return usuarioRepository.findAllByEmailIn(emailsReq).stream()
                .collect(Collectors.toMap(Usuario::getEmail, u -> u));
    }

    private void processarUsuariosAdicionados(
            final Equipe equipeDestino,
            final Set<UsuarioEquipeRequest> usuariosReq,
            final Map<String, Usuario> usuariosEncontrados,
            Set<Usuario> adicionados,
            Set<String> naoEncontrados,
            Set<String> jaEmOutroTime,
            Set<String> jaNaEquipe
    ) {

        for (UsuarioEquipeRequest req : usuariosReq) {

            Usuario usuario = usuariosEncontrados.get(req.email());

            if (usuario == null) { //usuário não exsite no db
                naoEncontrados.add(req.email());
            }
            else if (usuario.getEquipe() != null
                    && usuario.getEquipe().getId().equals(equipeDestino.getId())) { // Usuário já está na equipe
                jaNaEquipe.add(req.email());
            }
            else if (usuario.getEquipe() != null) { // Está em outra equipe
                jaEmOutroTime.add(req.email());
            }
            else { // adicionar na equipe
                usuario.setCargo(req.cargo());
                equipeDestino.adicionarUsuario(usuario);
                adicionados.add(usuario);
            }
        }
    }

    private Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(UsuarioNotFoundException::new);
    }

    private Equipe findEquipeById(Long id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new EquipeNotFoundException(id));
    }

}
