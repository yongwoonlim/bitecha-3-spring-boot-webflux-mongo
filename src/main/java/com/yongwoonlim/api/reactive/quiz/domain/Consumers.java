package com.yongwoonlim.api.reactive.quiz.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Consumers {
    @KafkaListener(topics = "kafka-test")
    public void listenGroup(String message) {
        log.info("Received message: " + message);
    }
}
