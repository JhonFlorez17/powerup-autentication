package co.com.powerup.usecase.users;

import co.com.powerup.model.rol.gateways.RolRepository;
import co.com.powerup.model.users.Users;
import co.com.powerup.model.users.exception.UserValidationException;
import co.com.powerup.model.users.gateways.UsersRepository;
import co.com.powerup.model.users.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {
  private final UsersRepository userRepository;
  private final RolRepository rolRepository;

  public Mono<Users> execute(Users user) {
    UserValidator.validate(user);

    return userRepository.findByEmail(user.getEmail())
      .flatMap(existing -> Mono.<Users>error(new UserValidationException("El email ya está registrado")))
      .switchIfEmpty(
              rolRepository.findByIdRol(user.getRole().getId())
                      .switchIfEmpty(Mono.error(new UserValidationException("El rol asignado no existe")))
                      .flatMap(rol -> {
                        Users userWithRol = user.toBuilder().role(rol).build();
                        return userRepository.saveUser(userWithRol);
                      })
      );
  }
}
