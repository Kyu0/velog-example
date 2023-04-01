package com.example.mqpub.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {
    
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public EventDispatcher(RabbitTemplate rabbitTemplate, @Value("${exchange.name}") String exchangeName, @Value("${routing.key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void send(String message) {
        // "exchangeName" 을 가진 Exchange에게 메시지 전송, "routingKey" 를 기반으로 매칭되는 큐에게 전달 예정
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
