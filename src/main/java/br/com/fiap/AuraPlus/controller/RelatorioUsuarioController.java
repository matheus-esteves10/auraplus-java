package br.com.fiap.AuraPlus.controller;

import br.com.fiap.AuraPlus.dto.response.RelatorioUsuarioLeituraDto;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.service.RelatorioUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios/usuario")
@SecurityRequirement(name = "bearerAuth")
public class RelatorioUsuarioController {

    private final RelatorioUsuarioService relatorioUsuarioService;

    public RelatorioUsuarioController(RelatorioUsuarioService relatorioUsuarioService) {
        this.relatorioUsuarioService = relatorioUsuarioService;
    }

    @Operation(
            summary = "Obter relatório mensal de um usuário",
            description = "Retorna o relatório referente ao usuário informado, filtrando pelo mês e ano."
    )
    @GetMapping()
    public ResponseEntity<RelatorioUsuarioLeituraDto> getRelatorioPorUsuario(
            @AuthenticationPrincipal Usuario usuario,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano
    ) {
        final int mesFiltro = (mes != null) ? mes : LocalDate.now().getMonthValue();
        final int anoFiltro = (ano != null) ? ano : LocalDate.now().getYear();
        RelatorioUsuarioLeituraDto relatorio = relatorioUsuarioService.getRelatorioByUsuarioId(usuario.getId(), mesFiltro, anoFiltro);
        return ResponseEntity.ok(relatorio);
    }
}
