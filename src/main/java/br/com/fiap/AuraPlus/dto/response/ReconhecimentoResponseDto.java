package br.com.fiap.AuraPlus.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReconhecimentoResponseDto(String nomeReconhecido,
                                        String titulo,
                                        String descricao,
                                        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime data) {
}
