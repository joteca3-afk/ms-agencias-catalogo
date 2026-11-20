package com.oteca.agenciascatalogo.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AgenciaContactoUpdateDTO {

    @NotBlank(message = "La dirección no puede quedar vacía.")
    private String direccion;

    @NotBlank(message = "El teléfono de contacto es obligatorio.")
    private String telefono;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo ingresado no tiene un formato válido.")
    private String correo;

    private String sitioWeb;

    private String redesSociales;

    private String descripcion;
}
