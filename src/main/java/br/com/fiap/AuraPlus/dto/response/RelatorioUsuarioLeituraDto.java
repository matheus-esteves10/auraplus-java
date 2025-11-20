package br.com.fiap.AuraPlus.dto.response;

import java.time.Year;

public record RelatorioUsuarioLeituraDto(int numeroIndicacoes,
                                         String descritivo,
                                         String nomeUsuario,
                                         String mes,
                                         Year ano) {
}
