package com.example.mqpub.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.mqpub.event.EventDispatcher;

@Service
public class SendService {

    private final EventDispatcher eventDispatcher;

    public SendService(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    // 3초마다 메시지 전송
    @Scheduled(fixedDelay = 3000)
    public void send() {
        eventDispatcher.send("test message sent.");
    }
}
