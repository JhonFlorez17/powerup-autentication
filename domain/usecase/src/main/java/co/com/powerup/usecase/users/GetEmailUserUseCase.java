package co.com.powerup.usecase.users;

import co.com.powerup.model.users.Users;
import co.com.powerup.model.users.gateways.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetEmailUserUseCase {

  private final UsersRepository userRepository;

  public Mono<Users> execute(String email) {

    return userRepository.findByEmail(email)
            .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado con email: " + email)));

  }

}
