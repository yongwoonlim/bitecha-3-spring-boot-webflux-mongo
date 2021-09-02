package com.yongwoonlim.api.reactive.quiz.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@RequiredArgsConstructor
public class Producers {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String payload) {
        ListenableFuture<SendResult<String, String>> listenable = kafkaTemplate.send(topic, payload);
    }
}
