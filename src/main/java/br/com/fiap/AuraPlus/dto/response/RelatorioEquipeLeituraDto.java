package br.com.fiap.AuraPlus.dto.response;

import java.time.Year;

public record RelatorioEquipeLeituraDto(String mesReferente,
                                        Year anoReferente,
                                        String nomeEquipe,
                                        String sentimentoMedio,
                                        String descritivo,
                                        Integer totalReports) {
}
