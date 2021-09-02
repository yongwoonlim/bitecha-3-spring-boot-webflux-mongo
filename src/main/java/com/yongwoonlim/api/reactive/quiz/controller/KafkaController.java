package com.yongwoonlim.api.reactive.quiz.controller;

import com.yongwoonlim.api.reactive.quiz.domain.Producers;
import com.yongwoonlim.api.reactive.quiz.service.KafkaSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaSender kafkaSender;
    private final Producers producers;

    @GetMapping
    public String hello() {
        return "Hello Kafka";
    }

    @GetMapping("/producer")
    public String producer(@RequestParam("message") String message) {
        log.debug("RequestParam: " + message);
        kafkaSender.send(message);
        return "Message sent to kafka: " + message;
    }

    @GetMapping("/receiver")
    public void receiver() {
        producers.sendMessage("kafka-test", "Good-Luck !!");
    }
}
