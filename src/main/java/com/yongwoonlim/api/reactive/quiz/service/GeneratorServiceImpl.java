package com.yongwoonlim.api.reactive.quiz.service;

import com.yongwoonlim.api.reactive.quiz.domain.Factor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.function.Function;

@Service
public class GeneratorServiceImpl implements GeneratorService {
    @Override
    public int randomFactor() {
        Function<String, Integer> function = Integer::parseInt;
        return new Random().nextInt(function.apply(Factor.MAXIMUM.toString()) - function.apply(Factor.MINIMUM.toString()) + 1)
                + function.apply(Factor.MINIMUM.toString());
    }
}