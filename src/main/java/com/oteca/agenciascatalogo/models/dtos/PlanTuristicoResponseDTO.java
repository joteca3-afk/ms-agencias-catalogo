package com.oteca.agenciascatalogo.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PlanTuristicoResponseDTO {
    private Long id;
    private String nombre;
    private Long agenciaId;
    private String nombreAgencia;
    private String municipioDestino;
    private Long categoriaId;
    private String nombreCategoria;
    private BigDecimal precio;
    private Integer duracionDias;
    private Integer cupoMaximo;
    private Double calificacionPromedio;
    private Boolean destacado;
    private String hotel;
    private String transporte;
    private String guia;
    private String descripcion;
    private String itinerario;
    private String incluye;
    private String noIncluye;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}
