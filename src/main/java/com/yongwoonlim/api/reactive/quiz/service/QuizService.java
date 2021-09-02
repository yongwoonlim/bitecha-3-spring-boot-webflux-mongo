package com.yongwoonlim.api.reactive.quiz.service;

import com.yongwoonlim.api.reactive.quiz.domain.Quiz;
import reactor.core.publisher.Mono;

public interface QuizService {
    Mono<Quiz> createQuiz();
}