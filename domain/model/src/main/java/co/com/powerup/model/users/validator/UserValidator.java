package co.com.powerup.model.users.validator;

import co.com.powerup.model.users.Users;
import co.com.powerup.model.users.exception.UserValidationException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

public class UserValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static void validate(Users user) {
        if (Objects.isNull(user)) {
            throw new UserValidationException("El usuario no puede ser nulo");
        }
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new UserValidationException("El nombre es obligatorio");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new UserValidationException("El apellido es obligatorio");
        }
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new UserValidationException("El correo no es válido");
        }
        if (user.getBaseSalary() == null || user.getBaseSalary().compareTo(BigDecimal.ZERO) <= 0 ) {
            throw new UserValidationException("El salario base debe ser mayor a cero");
        }
        if (user.getRole() == null || user.getRole().getId() == null) {
            throw new UserValidationException("El rol del usuario es obligatorio");
        }
    }
}
