package br.com.fiap.AuraPlus.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${broker.exchange.relatorio}")
    private String exchangeName;

    @Value("${broker.queue.relatorio.usuario}")
    private String filaUsuario;

    @Value("${broker.queue.relatorio.equipe}")
    private String filaEquipe;

    @Value("${rabbit.routing.pessoa}")
    private String routingKeyPessoa;

    @Value("${rabbit.routing.equipe}")
    private String routingKeyEquipe;

    @Bean
    public TopicExchange relatorioExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Queue relatorioUsuarioQueue() {
        return QueueBuilder.durable(filaUsuario).build();
    }

    @Bean
    public Queue relatorioEquipeQueue() {
        return QueueBuilder.durable(filaEquipe).build();
    }

    @Bean
    public Binding bindingUsuario(TopicExchange relatorioExchange, Queue relatorioUsuarioQueue) {
        return BindingBuilder.bind(relatorioUsuarioQueue)
                .to(relatorioExchange)
                .with(routingKeyPessoa);
    }

    @Bean
    public Binding bindingEquipe(TopicExchange relatorioExchange, Queue relatorioEquipeQueue) {
        return BindingBuilder.bind(relatorioEquipeQueue)
                .to(relatorioExchange)
                .with(routingKeyEquipe);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
