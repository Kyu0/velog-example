package com.example.mqpub.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfiguration {
    
    private final String exchangeName;

    public RabbitMQConfiguration(@Value("${exchange.name}") String exchangeName) {
        this.exchangeName = exchangeName;
    }

    // TopicExchange 생성
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // Json 포맷으로 데이터 전송하도록 설정
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        
        rabbitTemplate.setMessageConverter(producerJackson2JsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
