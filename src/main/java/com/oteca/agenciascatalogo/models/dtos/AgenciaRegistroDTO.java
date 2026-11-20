package com.oteca.agenciascatalogo.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AgenciaRegistroDTO {

    @NotBlank(message = "El nombre de la agencia es obligatorio y no puede quedar en blanco.")
    @Size(max = 150, message = "El nombre no puede exceder los 150 caracteres.")
    private String nombre;

    @NotBlank(message = "El NIT de la agencia es obligatorio.")
    @Size(max = 25, message = "El NIT no puede superar los 25 caracteres.")
    private String nit;

    @NotBlank(message = "El Registro Nacional de Turismo (RNT) es obligatorio.")
    @Size(max = 30, message = "El RNT no puede superar los 30 caracteres.")
    private String rnt;

    @NotBlank(message = "La ciudad o municipio del Huila es obligatorio (ej. Neiva, Pitalito, Garzón).")
    private String ciudad;

    @NotBlank(message = "La dirección física de la agencia es obligatoria.")
    private String direccion;

    @NotBlank(message = "El teléfono de contacto es obligatorio.")
    private String telefono;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo ingresado no tiene un formato válido (ejemplo@agencia.co).")
    private String correo;

    private String sitioWeb;

    private String redesSociales;

    @NotBlank(message = "La descripción de la agencia y su especialidad turística es obligatoria.")
    private String descripcion;

    @NotNull(message = "El año de fundación es obligatorio.")
    @Min(value = 1950, message = "El año de fundación debe ser mayor o igual a 1950.")
    @Max(value = 2026, message = "El año de fundación no puede ser mayor al año actual.")
    private Integer anioFundacion;
}
