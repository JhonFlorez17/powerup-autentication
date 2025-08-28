package co.com.powerup.usecase.rol;

import co.com.powerup.model.rol.Rol;
import co.com.powerup.model.rol.gateways.RolRepository;
import co.com.powerup.model.users.Users;
import co.com.powerup.model.users.exception.UserValidationException;
import co.com.powerup.model.users.gateways.UsersRepository;
import co.com.powerup.model.users.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RolUseCase {

  private final RolRepository rolRepository;

  public Mono<Rol> getRoleById(Long id) {

    return rolRepository.findByIdRol(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Rol no encontrado con id: " + id)));
  }
}
