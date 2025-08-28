package co.com.powerup.model.users;
import co.com.powerup.model.rol.Rol;
import lombok.*;

import javax.management.relation.Role;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
//import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Users {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal baseSalary;
    private Rol role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
