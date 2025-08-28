package co.com.powerup.r2dbc;

import co.com.powerup.r2dbc.entities.UsersEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface UserReactiveRepository extends ReactiveCrudRepository<UsersEntity, Long>, ReactiveQueryByExampleExecutor<UsersEntity> {
  Mono<UsersEntity> findByEmail(String email);
}
