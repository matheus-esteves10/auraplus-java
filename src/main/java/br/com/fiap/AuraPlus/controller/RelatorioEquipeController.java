package br.com.fiap.AuraPlus.controller;

import br.com.fiap.AuraPlus.dto.response.RelatorioEquipeLeituraDto;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.service.RelatorioEquipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios/equipe")
@SecurityRequirement(name = "bearerAuth")
public class RelatorioEquipeController {

    private final RelatorioEquipeService relatorioEquipeService;

    public RelatorioEquipeController(RelatorioEquipeService relatorioEquipeService) {
        this.relatorioEquipeService = relatorioEquipeService;
    }

    @GetMapping()
    @Operation(
            summary = "Obter relatório mensal de uma equipe",
            description = "Retorna o relatório referente à equipe informada. " +
                    "É possível passar os parâmetros 'mes' e 'ano' como query params. " +
                    "Se não forem informados, serão utilizados o mês e ano atuais. Ex: /relatorios/equipe?mes=11&ano=2025"
    )
    public ResponseEntity<RelatorioEquipeLeituraDto> getRelatorio(
            @AuthenticationPrincipal Usuario usuario,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano
    ) {
        final int mesFiltro = (mes != null) ? mes : LocalDate.now().getMonthValue();
        final int anoFiltro = (ano != null) ? ano : LocalDate.now().getYear();

        final RelatorioEquipeLeituraDto relatorio = relatorioEquipeService.getRelatorioByEquipeAndMes(usuario.getEquipe().getId(), mesFiltro, anoFiltro);
        return ResponseEntity.ok(relatorio);
    }
}
