package com.yongwoonlim.api.reactive.quiz.repository;

import com.yongwoonlim.api.reactive.quiz.domain.Attempt;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AttemptRepository extends ReactiveMongoRepository<Attempt, String> {
    Flux<Attempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}
