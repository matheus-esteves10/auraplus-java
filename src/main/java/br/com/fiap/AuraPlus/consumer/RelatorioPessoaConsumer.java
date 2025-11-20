package br.com.fiap.AuraPlus.consumer;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioPessoaDto;
import br.com.fiap.AuraPlus.service.IaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RelatorioPessoaConsumer {

    private final IaService iaService;

    public RelatorioPessoaConsumer(IaService iaService) {
        this.iaService = iaService;
    }

    @RabbitListener(queues = "${broker.queue.relatorio.usuario}")
    public void receber(RelatorioPessoaDto dto) {

        System.out.println("Titulos: " + dto.titulos());
        System.out.println("Descrições: " + dto.descritivo());
        System.out.println("Número indicações: " + dto.numeroIndicacoes());
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
    }
}
