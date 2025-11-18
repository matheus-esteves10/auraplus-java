package br.com.fiap.AuraPlus.controller;

import br.com.fiap.AuraPlus.dto.request.CadastroReconhecimentoDto;
import br.com.fiap.AuraPlus.dto.response.ReconhecimentoResponseDto;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.service.ReconhecimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reconhecimento")
@SecurityRequirement(name = "bearerAuth")
public class ReconhecimentoController {

    private final ReconhecimentoService reconhecimentoService;

    public ReconhecimentoController(ReconhecimentoService reconhecimentoService) {
        this.reconhecimentoService = reconhecimentoService;
    }

    @PostMapping("/{idReconhecido}")
    @Operation(
            summary = "Reconhece um colaborador (permitido apenas 1 vez por mês)"
    )
    public ResponseEntity<ReconhecimentoResponseDto> reconhecerColaborador(@AuthenticationPrincipal final Usuario usuarioLogado,
                                                                           @PathVariable final Long idReconhecido,
                                                                           @RequestBody @Valid final CadastroReconhecimentoDto dto) {

        final ReconhecimentoResponseDto response = reconhecimentoService.reconhecerColaborador(usuarioLogado.getId(), dto, idReconhecido);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
