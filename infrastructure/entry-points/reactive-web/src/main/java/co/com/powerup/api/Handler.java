package co.com.powerup.api;

import co.com.powerup.api.dto.UserRequestDTO;
import co.com.powerup.api.mapper.UserMapper;
import co.com.powerup.usecase.users.GetEmailUserUseCase;
import co.com.powerup.usecase.users.UserUseCase;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {
    private final UserUseCase userUseCase;
    private final GetEmailUserUseCase getEmailUserUseCase;
    private final UserMapper userMapper;
    private final Validator validator;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserRequestDTO.class)
                .flatMap(dto -> {
                    var violations = validator.validate(dto);
                    if (!violations.isEmpty()) {
                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(violations);
                    }

                    return userUseCase.execute(userMapper.toDomain(dto))
                            .map(userMapper::toResponse)
                            .flatMap(res -> ServerResponse.status(201)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(res));
                })
                .onErrorResume(e -> {
                    log.error("Error creando usuario: {}", e.getMessage(), e);
                    return ServerResponse.status(400)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("Error al crear usuario: " + e.getMessage());
                });
    }

    public Mono<ServerResponse> getUserByEmail(ServerRequest request) {
        String email = request.pathVariable("email");

        return getEmailUserUseCase.execute(email)
                .map(userMapper::toResponse)
                .flatMap(res -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(res))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> {
                    log.error("Error obteniendo usuario: {}", e.getMessage(), e);
                    return ServerResponse.status(400)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("Error al obtener usuario: " + e.getMessage());
                });
    }

}
