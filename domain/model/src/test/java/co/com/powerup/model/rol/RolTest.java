package co.com.powerup.model.rol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class RolTest {

  @Test
  void shouldBuildRolSuccessfully() {
    LocalDateTime now = LocalDateTime.now();

    Rol rol = Rol.builder()
            .id(1L)
            .name("ADMIN")
            .description("Administrador del sistema")
            .createdAt(now)
            .updatedAt(now)
            .build();

    assertNotNull(rol);
    assertEquals(1L, rol.getId());
    assertEquals("ADMIN", rol.getName());
    assertEquals("Administrador del sistema", rol.getDescription());
    assertEquals(now, rol.getCreatedAt());
    assertEquals(now, rol.getUpdatedAt());
  }

  @Test
  void shouldSettersAndGettersWorkCorrectly() {
    Rol rol = new Rol();
    rol.setId(2L);
    rol.setName("USER");
    rol.setDescription("Usuario estándar");

    assertEquals(2L, rol.getId());
    assertEquals("USER", rol.getName());
    assertEquals("Usuario estándar", rol.getDescription());
  }


}
