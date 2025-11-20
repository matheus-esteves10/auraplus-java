package br.com.fiap.AuraPlus.producer;

import br.com.fiap.AuraPlus.dto.broker.producer.RelatorioEquipeDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RelatorioEquipeProducer extends  BaseProducer<RelatorioEquipeDto> {

    public RelatorioEquipeProducer(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Value("${broker.exchange.relatorio}")
    private String exchange;

    @Value("${rabbit.routing.equipe}")
    private String routingKey;

    @Override
    public void publishMessage(final RelatorioEquipeDto message) {
        send(exchange, routingKey, message);
    }
}
