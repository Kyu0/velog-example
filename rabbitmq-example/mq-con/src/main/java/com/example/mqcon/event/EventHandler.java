package com.example.mqcon.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EventHandler {

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(String message) {
        log.info("message received : {}", message);
    }
}
