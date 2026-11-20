package com.oteca.agenciascatalogo.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "agencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "nit", nullable = false, unique = true, length = 25)
    private String nit;

    @Column(name = "rnt", nullable = false, unique = true, length = 30)
    private String rnt;

    @Column(name = "ciudad", nullable = false, length = 80)
    private String ciudad;

    @Column(name = "direccion", nullable = false, length = 180)
    private String direccion;

    @Column(name = "telefono", nullable = false, length = 30)
    private String telefono;

    @Column(name = "correo", nullable = false, unique = true, length = 120)
    private String correo;

    @Column(name = "sitio_web", length = 200)
    private String sitioWeb;

    @Column(name = "redes_sociales", length = 255)
    private String redesSociales;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "anio_fundacion")
    private Integer anioFundacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoAgencia estado;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_modificacion", nullable = false)
    private LocalDateTime ultimaModificacion;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoAgencia.PENDIENTE_APROBACION;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.ultimaModificacion = LocalDateTime.now();
    }
}
