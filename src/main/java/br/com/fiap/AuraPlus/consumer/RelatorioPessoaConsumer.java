package br.com.fiap.AuraPlus.consumer;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioPessoaDto;
import br.com.fiap.AuraPlus.service.IaService;
import br.com.fiap.AuraPlus.service.RelatorioUsuarioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RelatorioPessoaConsumer {

    private final IaService iaService;

    private final RelatorioUsuarioService relatorioUsuarioService;

    public RelatorioPessoaConsumer(IaService iaService, RelatorioUsuarioService relatorioUsuarioService) {
        this.iaService = iaService;
        this.relatorioUsuarioService = relatorioUsuarioService;
    }

    @RabbitListener(queues = "${broker.queue.relatorio.usuario}")
    public void receber(RelatorioPessoaDto dto) {
        final String retornoIa = iaService.processarRelatorioPessoa(dto);
        relatorioUsuarioService.saveRelatorioUsuario(dto, retornoIa);

    }
}
