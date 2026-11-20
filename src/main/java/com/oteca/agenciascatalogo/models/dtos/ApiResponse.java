package com.oteca.agenciascatalogo.models.dtos;

import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * Contenedor Universal (Wrapper JSON) según la Guía de Aprendizaje SENA (GFPI-F-135 V04).
 */
@Data
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private ApiError error;
    private ApiMeta meta = new ApiMeta();

    // CONSTRUCTOR 1: Petición EXITOSA (success = true)
    public ApiResponse(T data) {
        this.success = true;
        this.data = data;
        this.error = null;
    }

    // CONSTRUCTOR 2: Petición FALLIDA (success = false)
    public ApiResponse(String code, String message, Map<String, String> details) {
        this.success = false;
        this.data = null;
        this.error = new ApiError(code, message, details);
    }

    @Data
    public static class ApiError {
        private final String code;
        private final String message;
        private final Map<String, String> details;
    }

    @Data
    public static class ApiMeta {
        private final String timestamp = Instant.now().toString();
        private final String apiVersion = "v1.0";
    }
}
