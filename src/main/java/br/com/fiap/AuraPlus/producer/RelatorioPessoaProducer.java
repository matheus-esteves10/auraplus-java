package br.com.fiap.AuraPlus.producer;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioPessoaDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RelatorioPessoaProducer extends BaseProducer<RelatorioPessoaDto> {

    public RelatorioPessoaProducer(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Value("${broker.exchange.relatorio}")
    private String exchange;

    @Value("${rabbit.routing.pessoa}")
    private String routingKey;

    @Override
    public void publishMessage(final RelatorioPessoaDto message) {
        send(exchange, routingKey, message);
    }
}
