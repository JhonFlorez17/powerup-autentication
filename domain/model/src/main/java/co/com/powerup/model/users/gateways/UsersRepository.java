package co.com.powerup.model.users.gateways;

import co.com.powerup.model.users.Users;
import reactor.core.publisher.Mono;

public interface UsersRepository {

    Mono<Users> saveUser(Users user);
    Mono<Users> findByEmail(String email);

}
