package com.oteca.agenciascatalogo.exceptions;

import com.oteca.agenciascatalogo.models.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Escudo Global de Excepciones (@RestControllerAdvice) según la Guía SENA (GFPI-F-135 V04).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Errores de validación de formulario (@Valid) -> HTTP 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> manejarErroresDeFormulario(MethodArgumentNotValidException ex) {
        Map<String, String> listaDeErrores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            listaDeErrores.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Void> respuestaConError = new ApiResponse<>(
                "VALIDATION_ERROR",
                "Los datos enviados en el formulario son inválidos.",
                listaDeErrores
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuestaConError);
    }

    // 2. Recurso no encontrado -> HTTP 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> manejarRecursoNoEncontrado(ResourceNotFoundException ex) {
        ApiResponse<Void> respuestaConError = new ApiResponse<>(
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(respuestaConError);
    }

    // 3. Conflicto de reglas de negocio (Duplicados) -> HTTP 409 Conflict
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> manejarRecursoDuplicado(DuplicateResourceException ex) {
        Map<String, String> detalle = new HashMap<>();
        detalle.put(ex.getCampo(), ex.getMessage());

        ApiResponse<Void> respuestaConError = new ApiResponse<>(
                "DUPLICATE_RESOURCE",
                ex.getMessage(),
                detalle
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(respuestaConError);
    }

    // 4. Error interno no previsto (sin exponer StackTrace) -> HTTP 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> manejarErrorGeneral(Exception ex) {
        ApiResponse<Void> respuestaConError = new ApiResponse<>(
                "INTERNAL_SERVER_ERROR",
                "Ocurrió un error interno procesando la solicitud en el servidor.",
                null
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(respuestaConError);
    }
}
