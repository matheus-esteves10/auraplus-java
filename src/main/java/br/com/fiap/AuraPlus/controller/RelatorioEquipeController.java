package br.com.fiap.AuraPlus.controller;

import br.com.fiap.AuraPlus.dto.response.RelatorioLeituraDto;
import br.com.fiap.AuraPlus.service.RelatorioEquipeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios")
@SecurityRequirement(name = "bearerAuth")
public class RelatorioEquipeController {

    private final RelatorioEquipeService relatorioEquipeService;

    public RelatorioEquipeController(RelatorioEquipeService relatorioEquipeService) {
        this.relatorioEquipeService = relatorioEquipeService;
    }

    @GetMapping
    public ResponseEntity<RelatorioLeituraDto> getRelatorio(
            @RequestParam Long equipeId,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano
    ) {
        int mesFiltro = (mes != null) ? mes : LocalDate.now().getMonthValue();
        int anoFiltro = (ano != null) ? ano : LocalDate.now().getYear();

        RelatorioLeituraDto relatorio = relatorioEquipeService.getRelatorioByEquipeAndMes(equipeId, mesFiltro, anoFiltro);
        return ResponseEntity.ok(relatorio);
    }
}
