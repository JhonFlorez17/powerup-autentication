package co.com.powerup.r2dbc;

import co.com.powerup.r2dbc.entities.RolEntity;
import co.com.powerup.r2dbc.entities.UsersEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RolReactiveRepository extends ReactiveCrudRepository<RolEntity, Long>, ReactiveQueryByExampleExecutor<RolEntity> {
}
