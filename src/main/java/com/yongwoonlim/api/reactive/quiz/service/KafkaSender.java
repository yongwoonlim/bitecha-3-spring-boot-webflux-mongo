package com.yongwoonlim.api.reactive.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    String kafkaTopic = "hello-kafka";

    public void send(String data){
        kafkaTemplate.send(kafkaTopic, data);
    }
}
