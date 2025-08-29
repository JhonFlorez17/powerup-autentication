package co.com.powerup.usecase.users;

import co.com.powerup.model.rol.Rol;
import co.com.powerup.model.rol.gateways.RolRepository;
import co.com.powerup.model.users.Users;
import co.com.powerup.model.users.exception.UserValidationException;
import co.com.powerup.model.users.gateways.UsersRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class UsersUseCaseTest {
  private UsersRepository userRepository;
  private RolRepository rolRepository;
  private UserUseCase userUseCase;

  private Users buildValidUser() {
    Rol role = Rol.builder()
            .id(1L)
            .name("ADMIN")
            .description("Administrador sistema")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    return Users.builder()
            .id(1L)
            .firstName("Carlos")
            .lastName("Pérez")
            .birthDate(LocalDate.of(1990, 1, 1))
            .email("carlos@example.com")
            .baseSalary(BigDecimal.valueOf(3000))
            .role(role)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @BeforeEach
  void setUp() {
    userRepository = mock(UsersRepository.class);
    rolRepository = mock(RolRepository.class);
    userUseCase = new UserUseCase(userRepository, rolRepository);
  }

  @Test
  void execute_usuarioValidoYRolExistente_guardaUsuario() {

    Users user = buildValidUser();
    Rol rol = user.getRole();

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.empty());
    when(rolRepository.findByIdRol(anyLong())).thenReturn(Mono.just(rol));
    when(userRepository.saveUser(any())).thenAnswer(invocation -> {
      Users u = invocation.getArgument(0, Users.class);
      return Mono.just(u);
    });

    StepVerifier.create(userUseCase.execute(user))
            .expectNextMatches(u -> u.getFirstName().equals("Carlos") &&
                    u.getRole() != null &&
                    u.getRole().getName().equals("ADMIN"))
            .verifyComplete();

    ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
    verify(userRepository).saveUser(captor.capture());
    Users usuarioGuardado = captor.getValue();
    assertEquals("Carlos", usuarioGuardado.getFirstName());
    assertEquals("ADMIN", usuarioGuardado.getRole().getName());
  }

  @Test
  void execute_conRolNoExistente_error() {

    Users user = buildValidUser();

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.empty());
    when(rolRepository.findByIdRol(anyLong())).thenReturn(Mono.empty());

    StepVerifier.create(userUseCase.execute(user))
            .expectError(UserValidationException.class)
            .verify();

    verify(userRepository, never()).saveUser(any());
  }

  @Test
  void execute_conEmailDuplicado_error() {

    Users user = buildValidUser();

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.just(user));
    when(rolRepository.findByIdRol(anyLong())).thenReturn(Mono.just(user.getRole())); // <-- necesario

    StepVerifier.create(userUseCase.execute(user))
            .expectError(UserValidationException.class)
            .verify();

    verify(userRepository, never()).saveUser(any());
  }

//  @Test
//  void execute_conUsuarioInvalido_errorValidacion() {
//    Users user = buildValidUser();
//    user.setEmail("correo-invalido");
//
//    StepVerifier.create(userUseCase.execute(user))
//            .expectErrorMatches(ex -> ex instanceof UserValidationException &&
//                    ex.getMessage().equals("El correo no es válido"))
//            .verify();
//
//    verify(userRepository, never()).saveUser(any());
//    verifyNoInteractions(rolRepository); // opcional, para confirmar que nunca se toca
//  }
}
