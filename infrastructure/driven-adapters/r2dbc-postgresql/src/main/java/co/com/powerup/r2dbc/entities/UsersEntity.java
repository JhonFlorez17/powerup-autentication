package co.com.powerup.r2dbc.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "users")
public class UsersEntity {

  @Id
  private Long id;

  @Column("first_name")
  private String firstName;

  @Column("last_name")
  private String lastName;

  @Column("birth_date")
  private LocalDate birthDate;

  private String address;
  private String phone;
  private String email;

  @Column("base_salary")
  private BigDecimal baseSalary;

  @Column("id_role")
  private Long idRole;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
