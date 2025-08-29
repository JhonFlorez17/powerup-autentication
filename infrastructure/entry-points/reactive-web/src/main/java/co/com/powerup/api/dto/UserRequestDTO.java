package co.com.powerup.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema (description = "Request para crear un usuario")
public class UserRequestDTO {

  @NotBlank(message = "El nombre es obligatorio")
  @Schema(description = "Nombre del usuario", example = "Juan")
  private String firstName;

  @NotBlank(message = "El apellido es obligatorio")
  @Schema(description = "Apellido del usuario", example = "Pérez")
  private String lastName;

  private LocalDate birthDate;

  private String address;

  @Pattern(regexp = "^[0-9+\\- ]{7,20}$", message = "El teléfono no es válido")
  @Schema(description = "Telefono ceular", example = "3156667777")
  private String phone;

  @NotBlank(message = "El correo es obligatorio")
  @Email(message = "El correo no tiene un formato válido")
  @Schema(description = "Correo electrónico único", example = "juan@test.com")
  private String email;

  @NotNull(message = "El salario base es obligatorio")
  @DecimalMin(value = "0.01", message = "El salario debe ser mayor que 0")
  @DecimalMax(value = "15000000", message = "El salario no puede superar los 15 millones")
  @Schema(description = "Salario base (máx 15M)", example = "2500000")
  private BigDecimal baseSalary;

  @NotNull(message = "El rol es obligatorio")
  @Schema(description = "ID del rol asociado como usuario '2'", example = "2")
  private Long idRole;

}
