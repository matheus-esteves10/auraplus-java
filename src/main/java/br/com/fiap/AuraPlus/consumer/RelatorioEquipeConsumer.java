package br.com.fiap.AuraPlus.consumer;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioEquipeDto;
import br.com.fiap.AuraPlus.service.IaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RelatorioEquipeConsumer {

    private final IaService iaService;

    public RelatorioEquipeConsumer(IaService iaService) {
        this.iaService = iaService;
    }

    @RabbitListener(queues = "${broker.queue.relatorio.equipe}")
    public void receber(final RelatorioEquipeDto dto) {

    }
}
