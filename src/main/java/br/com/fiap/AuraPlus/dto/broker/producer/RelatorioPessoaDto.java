package br.com.fiap.AuraPlus.dto.broker.producer;

import br.com.fiap.AuraPlus.model.Usuario;
import lombok.Builder;

@Builder
public record RelatorioPessoaDto(Long usuarioId,
                                 int numeroIndicacoes,
                                 String titulos,
                                 String descritivo) {
}
