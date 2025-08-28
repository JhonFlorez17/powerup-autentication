package co.com.powerup.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserRequestDTO {

  @NotBlank(message = "El nombre es obligatorio")
  private String firstName;

  @NotBlank(message = "El apellido es obligatorio")
  private String lastName;

  private LocalDate birthDate;

  private String address;

  @Pattern(regexp = "^[0-9+\\- ]{7,20}$", message = "El teléfono no es válido")
  private String phone;

  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "El correo no tiene un formato válido")
  private String email;

  @NotNull(message = "El salario base es obligatorio")
  @DecimalMin(value = "0.01", message = "El salario debe ser mayor que 0")
  @DecimalMax(value = "15000000", message = "El salario no puede superar los 15 millones")
  private BigDecimal baseSalary;

  @NotNull(message = "El rol es obligatorio")
  private Long idRole;

}
