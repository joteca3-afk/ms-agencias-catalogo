package com.oteca.agenciascatalogo.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriaResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String colorHex;
    private Boolean activa;
}
