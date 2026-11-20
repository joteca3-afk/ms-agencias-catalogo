package com.oteca.agenciascatalogo.models.dtos;

import com.oteca.agenciascatalogo.models.entities.EstadoAgencia;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AgenciaResponseDTO {
    private Long id;
    private String nombre;
    private String nit;
    private String rnt;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String correo;
    private String sitioWeb;
    private String redesSociales;
    private String descripcion;
    private Integer anioFundacion;
    private EstadoAgencia estado;
    private LocalDateTime fechaRegistro;
}
