package co.com.powerup.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {

  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  private String email;
  private BigDecimal baseSalary;
  private Long idRole;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
