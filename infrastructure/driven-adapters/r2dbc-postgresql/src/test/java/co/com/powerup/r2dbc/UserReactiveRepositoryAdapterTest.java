package co.com.powerup.r2dbc;

import co.com.powerup.model.users.Users;
import co.com.powerup.r2dbc.entities.UsersEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {
    @InjectMocks
    private UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    private UserReactiveRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private TransactionalOperator transactionalOperator;

    private Users user;
    private UsersEntity entity;

    @BeforeEach
    void init() {
        user = Users.builder()
                .id(1L)
                .firstName("Carlos")
                .lastName("Pérez")
                .email("carlos@example.com")
                .build();

        entity = UsersEntity.builder()
                .id(1L)
                .firstName("Carlos")
                .lastName("Pérez")
                .email("carlos@example.com")
                .build();

        // Para que .as(transactionalOperator::transactional) no falle con NullPointer
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void mustSaveUser() {
        when(repository.save(any(UsersEntity.class))).thenReturn(Mono.just(entity));
        when(repository.findById(1L)).thenReturn(Mono.just(entity));

        Mono<Users> result = repositoryAdapter.saveUser(user);

        StepVerifier.create(result)
                .expectNextMatches(saved ->
                        saved.getId().equals(1L) &&
                                saved.getEmail().equals("carlos@example.com"))
                .verifyComplete();
    }

    @Test
    void mustFindByEmail() {
        when(repository.findByEmail("carlos@example.com")).thenReturn(Mono.just(entity));

        Mono<Users> result = repositoryAdapter.findByEmail("carlos@example.com");

        StepVerifier.create(result)
                .expectNextMatches(found ->
                        found.getId().equals(1L) &&
                                found.getFirstName().equals("Carlos"))
                .verifyComplete();
    }
}
