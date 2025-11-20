package br.com.fiap.AuraPlus.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public abstract class BaseProducer<T> {

    private final RabbitTemplate rabbitTemplate;

    protected BaseProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    protected void send(final String exchange, final String routingKey, final T message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    public abstract void publishMessage(final T message);
}
