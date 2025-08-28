package co.com.powerup.r2dbc;

import co.com.powerup.model.users.Users;
import co.com.powerup.model.users.gateways.UsersRepository;
import co.com.powerup.r2dbc.entities.UsersEntity;
import co.com.powerup.r2dbc.helper.ReactiveAdapterOperations;
import co.com.powerup.r2dbc.mapper.UsserMapperInfra;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Users,
        UsersEntity,
        Long,
        UserReactiveRepository
        > implements UsersRepository {

  private final TransactionalOperator transactionalOperator;

  public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
    super(repository, mapper, UsserMapperInfra::toDomain);
    this.transactionalOperator = transactionalOperator;
   }

  @Override
  public Mono<Users> saveUser(Users user) {
    return repository.save(UsserMapperInfra.toData(user))
            .flatMap(saved -> repository.findById(saved.getId()))
            .map(UsserMapperInfra::toDomain)
            .as(transactionalOperator::transactional);
  }

  @Override
  public Mono<Users> findByEmail(String email) {
    return repository.findByEmail(email)
            .map(this::toEntity).as(transactionalOperator::transactional);
  }
}
