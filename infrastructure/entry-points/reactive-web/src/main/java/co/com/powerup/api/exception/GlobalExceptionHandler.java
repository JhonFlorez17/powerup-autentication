package co.com.powerup.api.exception;

import co.com.powerup.model.users.exception.UserValidationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public Mono<ResponseEntity<ApiErrorResponse>> handleValidation(WebExchangeBindException ex) {
    log.warn("Error de validación: {}", ex.getMessage());

    List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> new FieldErrorResponse(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());

    ApiErrorResponse response = ApiErrorResponse.builder()
            .success(false)
            .message("Errores de validación")
            .errors(errors)
            .build();

    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
  }

  @ExceptionHandler(UserValidationException.class)
  public Mono<ResponseEntity<ApiErrorResponse>> handleBusiness(UserValidationException ex) {
    log.warn("Error de negocio: {}", ex.getMessage());

    ApiErrorResponse response = ApiErrorResponse.builder()
            .success(false)
            .message(ex.getMessage())
            .build();

    return Mono.just(ResponseEntity.badRequest().body(response));
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<ApiErrorResponse>> handleGeneric(Exception ex) {
    log.error("Error inesperado", ex);

    ApiErrorResponse response = ApiErrorResponse.builder()
            .success(false)
            .message("Ha ocurrido un error inesperado. Intente más tarde.")
            .build();

    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
  }

  @Data
  @Builder
  public static class ApiErrorResponse {
    private boolean success;
    private String message;
    private List<FieldErrorResponse> errors;
  }

  @Data
  @AllArgsConstructor
  public static class FieldErrorResponse {
    private String field;
    private String error;
  }
}
