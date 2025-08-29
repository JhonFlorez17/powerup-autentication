package co.com.powerup.api;

import co.com.powerup.api.dto.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
class RouterRestTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private Handler handler;

  @Test
  void testCreateUser_success() {
    // Arrange: DTO válido
    UserRequestDTO request = new UserRequestDTO(
            "Carlos",
            "Pérez",
            LocalDate.parse("1999-01-01"),
            "Cra 12 15 77",
            "3155556677",
            "carlos@example.com",
            new BigDecimal("1200000"),
            2L
    );

    when(handler.createUser(any()))
            .thenReturn(ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("Usuario guardado correctamente"));

    // Act & Assert
    webTestClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), UserRequestDTO.class)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("Usuario guardado correctamente");
  }

  @Test
  void testCreateUser_validationError() {
    // Arrange: DTO inválido (correo vacío, salario negativo)
    UserRequestDTO request = new UserRequestDTO(
            "Carlos",
            "Pérez",
            LocalDate.parse("1999-01-01"),
            "Cra 12 15 77",
            "3155556677",
            "", // <- correo inválido
            new BigDecimal("-500"),
            2L
    );

    when(handler.createUser(any()))
            .thenReturn(ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("Error de validación"));

    // Act & Assert
    webTestClient.post()
            .uri("/api/v1/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), UserRequestDTO.class)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(String.class)
            .isEqualTo("Error de validación");
  }
}
