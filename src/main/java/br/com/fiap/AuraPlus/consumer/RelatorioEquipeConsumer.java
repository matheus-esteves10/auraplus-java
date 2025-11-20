package br.com.fiap.AuraPlus.consumer;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioEquipeDto;
import br.com.fiap.AuraPlus.service.IaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RelatorioEquipeConsumer {

    private final IaService iaService;


    public RelatorioEquipeConsumer(IaService iaService) {
        this.iaService = iaService;
    }

    @RabbitListener(queues = "${broker.queue.relatorio.equipe}")
    public void receber(RelatorioEquipeDto dto) {
        System.out.println("Relatório da Equipe: " + dto.nomeEquipe());
        System.out.println("Número media sentimentos: " + dto.mediaSentimentos());
        System.out.println("Descritivo da equipe: " + dto.descritivo());
        System.out.println("Total Reports: " +dto.totalReports());
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
    }
}
