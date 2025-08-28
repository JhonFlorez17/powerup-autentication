package co.com.powerup.r2dbc;

import co.com.powerup.model.rol.Rol;
import co.com.powerup.model.rol.gateways.RolRepository;
import co.com.powerup.model.users.Users;
import co.com.powerup.r2dbc.entities.RolEntity;
import co.com.powerup.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Repository
public class RolReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Rol,
        RolEntity,
        Long,
        RolReactiveRepository
        > implements RolRepository {

  private final TransactionalOperator transactionalOperator;

  protected RolReactiveRepositoryAdapter(RolReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
    super(repository, mapper, d -> mapper.map(d, Rol.class));
    this.transactionalOperator = transactionalOperator;
  }

  @Override
  public Mono<Rol> findByIdRol(Long id) {
    return repository.findById(id)
            .map(this::toEntity)
            .as(transactionalOperator::transactional);
  }
}
