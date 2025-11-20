package br.com.fiap.AuraPlus.controller;

import br.com.fiap.AuraPlus.dto.request.CadastroEquipeDto;
import br.com.fiap.AuraPlus.dto.request.UsuarioEquipeRequest;
import br.com.fiap.AuraPlus.dto.response.CadastrarNaEquipeResponse;
import br.com.fiap.AuraPlus.dto.response.EquipeResponse;
import br.com.fiap.AuraPlus.dto.response.PageableEquipeResponse;
import br.com.fiap.AuraPlus.model.Equipe;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.service.EquipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/equipe")
@SecurityRequirement(name = "bearerAuth")
public class EquipeController {

    private final EquipeService equipeService;


    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping
    @PreAuthorize("hasRole('NOVO_USUARIO')")
    @Operation(
            summary = "Cria uma nova equipe e vincula o usuário logado como ADMIN"
    )
    public ResponseEntity<EquipeResponse> criarEquipe(@RequestBody @Valid final CadastroEquipeDto dto,
                                                      @AuthenticationPrincipal final Usuario usuarioLogado) {
        final Equipe equipeCriada = equipeService.cadastroEquipe(dto, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(EquipeResponse.from(equipeCriada));
    }

    @PostMapping("/usuarios")
    @CacheEvict(value = "usuarios", allEntries = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(
            summary = "Adiciona usuários à equipe do usuário logado"
    )
    public ResponseEntity<CadastrarNaEquipeResponse> adicionarUsuarios(@RequestBody @Valid final Set<UsuarioEquipeRequest> usuariosReq,
                                                                       @AuthenticationPrincipal final Usuario usuarioLogado) {

        final CadastrarNaEquipeResponse response = equipeService.adicionarUsuarios(usuarioLogado.getEquipe().getId(), usuariosReq);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/usuarios/{idRemover}")
    @CacheEvict(value = "usuarios", allEntries = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(
            summary = "Remove um usuário da equipe do usuário logado"
    )
    public ResponseEntity<Void> removerUsuario(@PathVariable Long idRemover, @AuthenticationPrincipal final Usuario usuarioLogado) {

        equipeService.removerUsuario(idRemover, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios")
    @Cacheable(value = "usuarios")
    @Operation(
            summary = "Lista os usuários da equipe do usuário logado (paginado)"
    )
    public ResponseEntity<PageableEquipeResponse> listarUsuarios(@AuthenticationPrincipal final Usuario usuarioLogado,
                                                                       final Pageable pageable) {
        final PageableEquipeResponse page =
                equipeService.listarUsuariosDaEquipe(usuarioLogado, pageable);

        return ResponseEntity.ok(page);
    }
}
