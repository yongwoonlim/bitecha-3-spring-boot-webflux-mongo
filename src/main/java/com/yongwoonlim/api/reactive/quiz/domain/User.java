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
@Document(collection = "users")
public class User implements Serializable {
    @Id
    private final String userId;
    private final String alias;
}
