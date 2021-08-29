package com.yongwoonlim.api.reactive.quiz.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@ToString
@RequiredArgsConstructor
@Document(collection = "attempts")
public class Attempt implements Serializable {
    @Id
    private int id;
    private final User user;
    private final Quiz quiz;
    private final int resultAttempt;
    private final boolean correct;
}
