package com.oteca.agenciascatalogo.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaRequestDTO {

    @NotBlank(message = "El nombre de la categoría de turismo es obligatorio.")
    @Size(max = 80, message = "El nombre no puede superar los 80 caracteres.")
    private String nombre;

    @NotBlank(message = "La descripción de la categoría es obligatoria.")
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres.")
    private String descripcion;

    private String colorHex = "#B84B26";
}
