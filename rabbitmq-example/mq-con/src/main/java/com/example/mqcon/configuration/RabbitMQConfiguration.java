package com.example.mqcon.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfiguration implements RabbitListenerConfigurer {
    
    private final String exchangeName;
    private final String queueName;
    private final String bindingKey;

    public RabbitMQConfiguration(@Value("${exchange.name}") String exchangeName, @Value("${queue.name}") String queueName, @Value("${binding.key}") String bindingKey) {
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.bindingKey = bindingKey;
    }

    // TopicExchange 생성
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // Consumer의 큐 생성
    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    // Exchange와 Queue 바인딩
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with(bindingKey);
    }


    // 아래의 메소드들은 데이터 포맷 컨버터 설정 및 RabbitMQListener 설정 메소드
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();

        factory.setMessageConverter(consumerJackson2MessageConverter());

        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
