package com.yongwoonlim.api.reactive.quiz.repository;

import com.yongwoonlim.api.reactive.quiz.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Optional<User> findByAlias(String alias);

}
