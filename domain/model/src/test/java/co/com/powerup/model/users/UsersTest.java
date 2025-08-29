package co.com.powerup.model.users;

import co.com.powerup.model.rol.Rol;
import co.com.powerup.model.users.exception.UserValidationException;
import co.com.powerup.model.users.validator.UserValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsersTest {

  private Users buildValidUser() {
    return Users.builder()
            .id(1L)
            .firstName("Andres")
            .lastName("Lopez")
            .birthDate(LocalDate.of(1990, 1, 1))
            .address("Calle 123")
            .phone("123456789")
            .email("andres@test.com")
            .baseSalary(BigDecimal.valueOf(2000))
            .role(Rol.builder()
                    .id(1L)
                    .name("ADMIN")
                    .description("Administrador sistema")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build())
            .build();
  }

  @Test
  void shouldValidateSuccessfullyWithValidData() {
    Users user = buildValidUser();

    assertDoesNotThrow(() -> UserValidator.validate(user));
    assertEquals("Andres", user.getFirstName());
    assertEquals("Lopez", user.getLastName());
    assertEquals("andres@test.com", user.getEmail());
  }

  @Test
  void shouldThrowExceptionWhenUserIsNull() {
    UserValidationException ex = assertThrows(UserValidationException.class,
            () -> UserValidator.validate(null));
    assertEquals("El usuario no puede ser nulo", ex.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenFirstNameIsMissing() {
    Users user = buildValidUser();
    user.setFirstName(null);

    UserValidationException ex = assertThrows(UserValidationException.class,
            () -> UserValidator.validate(user));
    assertEquals("El nombre es obligatorio", ex.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenLastNameIsMissing() {
    Users user = buildValidUser();
    user.setLastName("");

    UserValidationException ex = assertThrows(UserValidationException.class,
            () -> UserValidator.validate(user));
    assertEquals("El apellido es obligatorio", ex.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenEmailIsInvalid() {
    Users user = buildValidUser();
    user.setEmail("correo-invalido");

    UserValidationException ex = assertThrows(UserValidationException.class,
            () -> UserValidator.validate(user));
    assertEquals("El correo no es válido", ex.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenSalaryIsZeroOrNegative() {
    Users user = buildValidUser();
    user.setBaseSalary(BigDecimal.ZERO);

    UserValidationException ex = assertThrows(UserValidationException.class,
            () -> UserValidator.validate(user));
    assertEquals("El salario base debe ser mayor a cero", ex.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenRoleIsNull() {
    Users user = buildValidUser();
    user.setRole(null);

    UserValidationException ex = assertThrows(UserValidationException.class,
            () -> UserValidator.validate(user));
    assertEquals("El rol del usuario es obligatorio", ex.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenRoleIdIsNull() {
    Users user = buildValidUser();
    user.setRole(Rol.builder()
            .id(null)
            .name("ADMIN")
            .description("Administrador sistema")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build());

    UserValidationException ex = assertThrows(UserValidationException.class,
            () -> UserValidator.validate(user));
    assertEquals("El rol del usuario es obligatorio", ex.getMessage());
  }

}
