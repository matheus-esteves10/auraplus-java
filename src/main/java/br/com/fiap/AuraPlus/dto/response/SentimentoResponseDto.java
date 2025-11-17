package br.com.fiap.AuraPlus.dto.response;

import br.com.fiap.AuraPlus.model.enums.TipoSentimento;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record SentimentoResponseDto(TipoSentimento tipoSentimento,
                                    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime data) {}
