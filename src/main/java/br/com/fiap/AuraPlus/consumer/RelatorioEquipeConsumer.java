package br.com.fiap.AuraPlus.consumer;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioEquipeDto;
import br.com.fiap.AuraPlus.service.IaService;
import br.com.fiap.AuraPlus.service.RelatorioEquipeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RelatorioEquipeConsumer {

    private final IaService iaService;
    private final RelatorioEquipeService relatorioEquipeService;

    public RelatorioEquipeConsumer(IaService iaService, RelatorioEquipeService relatorioEquipeService) {
        this.iaService = iaService;
        this.relatorioEquipeService = relatorioEquipeService;
    }

    @RabbitListener(queues = "${broker.queue.relatorio.equipe}", concurrency = "1")
    public void receber(final RelatorioEquipeDto dto) {

       final String retornoIa = iaService.processarRelatorioEquipe(dto);

        relatorioEquipeService.saveRelatorioEquipe(retornoIa, dto);
        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
