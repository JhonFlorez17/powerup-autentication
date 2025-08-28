package co.com.powerup.api;

import co.com.powerup.api.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class RouterRest {

  @Bean
  @RouterOperations({
          @RouterOperation(
                  path = "/api/v1/usuarios",
                  beanClass = Handler.class,
                  beanMethod = "createUser",
                  operation = @Operation(
                          operationId = "saveUser",
                          summary = "Registrar un nuevo usuario",
                          description = "Recibe un objeto UsuarioRequestDTO y guarda un usuario en el sistema",
                          requestBody = @RequestBody(
                                  required = true,
                                  description = "Datos del usuario a registrar",
                                  content = @Content(schema = @Schema(implementation = UserRequestDTO.class))
                          ),
                          responses = {
                                  @ApiResponse(responseCode = "200", description = "Usuario guardado correctamente",
                                          content = @Content(mediaType = "application/json",
                                                  schema = @Schema(implementation = String.class))),
                                  @ApiResponse(responseCode = "400", description = "Error de validación",
                                          content = @Content(mediaType = "application/json",
                                                  schema = @Schema(implementation = String.class)))
                          }
                  )
          )
  })
  public RouterFunction<ServerResponse> routerFunction(Handler handler) {
    return RouterFunctions
            .route()
            .POST("/api/v1/usuarios", handler::createUser)
            .build();
  }
}
