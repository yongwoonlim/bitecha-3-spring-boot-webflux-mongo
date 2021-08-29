package com.yongwoonlim.api.reactive.quiz.service;

import com.yongwoonlim.api.reactive.quiz.domain.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final GeneratorService generatorService;

    @Override
    public Quiz createQuiz() {
        return new Quiz(
                generatorService.randomFactor(),
                generatorService.randomFactor()
        );
    }
}