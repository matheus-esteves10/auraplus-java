package br.com.fiap.AuraPlus.controller;

import br.com.fiap.AuraPlus.dto.request.CadastroUserDto;
import br.com.fiap.AuraPlus.dto.response.UsuarioResponse;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(
            summary = "Endpoint para criar um novo usuário"
    )
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody @Valid final CadastroUserDto cadastroUserDto) {
        final Usuario operadorCriado = usuarioService.salvarUsuario(cadastroUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.from(operadorCriado));
    }

    @PutMapping("/me")
    @Operation(
            summary = "Endpoint para atualizar o próprio usuário"
    )
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@RequestBody @Valid final CadastroUserDto cadastroUserDto,
                                                            @AuthenticationPrincipal final Usuario usuario) {
        final Usuario usuarioAtualizado = usuarioService.atualizarUsuario(cadastroUserDto, usuario);
        return ResponseEntity.ok(UsuarioResponse.from(usuarioAtualizado));
    }

    @GetMapping("/me")
    @Operation(
            summary = "Endpoint para obter as informações do próprio usuário"
    )
    public ResponseEntity<UsuarioResponse> obterInfosUsuario(@AuthenticationPrincipal final Usuario usuario) {
        final Usuario usuarioInfo = usuarioService.infosPessoaisUsuarioLogado(usuario.getEmail());
        return ResponseEntity.ok(UsuarioResponse.from(usuarioInfo));
    }

    @DeleteMapping("/me")
    @Operation(
            summary = "Endpoint para excluir o próprio usuário"
    )
    public ResponseEntity<Void> excluirUsuario(@AuthenticationPrincipal final Usuario usuario) {
        usuarioService.excluirUsuario(usuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("role/{idUser}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Operation(
            summary = "Endpoint para alterar role de um usuário",
            description = "Se gestor usarário se torna colaborador e vice-versa (necessário estar em uma equipe)"
    )
    public ResponseEntity<UsuarioResponse> alterarRole(@PathVariable Long idUser) {
        final Usuario usuarioAtualizado = usuarioService.alterarRole(idUser);
        return ResponseEntity.ok(UsuarioResponse.from(usuarioAtualizado));
    }
}
