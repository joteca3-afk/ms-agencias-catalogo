package com.oteca.agenciascatalogo.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanTuristicoRequestDTO {

    @NotBlank(message = "El nombre del plan turístico es obligatorio.")
    @Size(max = 150, message = "El nombre del plan no puede superar los 150 caracteres.")
    private String nombre;

    @NotNull(message = "El ID de la agencia propietaria del plan es obligatorio.")
    @Positive(message = "El ID de la agencia debe ser un número positivo.")
    private Long agenciaId;

    @NotBlank(message = "El municipio o destino en el Huila es obligatorio.")
    private String municipioDestino;

    @NotNull(message = "La categoría de turismo (ID) es obligatoria.")
    private Long categoriaId;

    @NotNull(message = "El precio por persona es obligatorio.")
    @Positive(message = "El precio debe ser un valor numérico positivo mayor a cero.")
    private BigDecimal precio;

    @NotNull(message = "La duración en días es obligatoria.")
    @Min(value = 1, message = "La duración mínima del plan turístico es de 1 día.")
    private Integer duracionDias;

    @NotNull(message = "El cupo máximo de personas es obligatorio.")
    @Min(value = 1, message = "El cupo máximo debe ser de al menos 1 persona.")
    private Integer cupoMaximo;

    private Boolean destacado = false;

    private String hotel;

    private String transporte;

    private String guia;

    @NotBlank(message = "La descripción detallada del plan es obligatoria.")
    private String descripcion;

    @NotBlank(message = "El itinerario del plan es obligatorio.")
    private String itinerario;

    private String incluye;

    private String noIncluye;
}
