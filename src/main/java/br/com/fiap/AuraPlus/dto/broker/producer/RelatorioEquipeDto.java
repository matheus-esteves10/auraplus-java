package br.com.fiap.AuraPlus.dto.broker.producer;

import br.com.fiap.AuraPlus.model.Equipe;
import br.com.fiap.AuraPlus.model.enums.TipoSentimento;
import lombok.Builder;

@Builder
public record RelatorioEquipeDto(Long idEquipe,
                                 String nomeEquipe,
                                 double mediaSentimentos,
                                 TipoSentimento modaSentimento,
                                 int totalReports,
                                 String sentimentosReportados,
                                 String descritivo) {
}
