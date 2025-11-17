package br.com.fiap.AuraPlus.controller;

import br.com.fiap.AuraPlus.dto.request.CadastroSentimentoDto;
import br.com.fiap.AuraPlus.dto.response.SentimentoResponseDto;
import br.com.fiap.AuraPlus.model.Sentimento;
import br.com.fiap.AuraPlus.model.Usuario;
import br.com.fiap.AuraPlus.service.SentimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sentimento")
@SecurityRequirement(name = "bearerAuth")
public class SentimentoController {

    private final SentimentoService sentimentoService;

    public SentimentoController(SentimentoService sentimentoService) {
        this.sentimentoService = sentimentoService;
    }

    @PostMapping
    @Operation(
            summary = "Registra o sentimento diário do usuário logado (somente 1 por dia)"
    )
    public ResponseEntity<SentimentoResponseDto> cadastrar(@RequestBody @Valid final CadastroSentimentoDto dto,
                                                           @AuthenticationPrincipal final Usuario usuarioLogado) {
        final SentimentoResponseDto response = sentimentoService.cadastrarSentimentoDiario(usuarioLogado, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    @Operation(
            summary = "Mostra o sentimento cadastrado pelo usuário no dia atual (se existir)"
    )
    public ResponseEntity<SentimentoResponseDto> mostrarHoje(@AuthenticationPrincipal final Usuario usuarioLogado) {
        final SentimentoResponseDto response = sentimentoService.mostrarSentimentoDiario(usuarioLogado);
        return ResponseEntity.ok(response);
    }
}
