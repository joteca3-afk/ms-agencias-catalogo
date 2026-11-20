package com.oteca.agenciascatalogo.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "planes_turisticos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanTuristico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agencia_id", nullable = false)
    private Agencia agencia;

    @Column(name = "municipio_destino", nullable = false, length = 100)
    private String municipioDestino;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;

    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;

    @Column(name = "calificacion_promedio", nullable = false)
    private Double calificacionPromedio;

    @Column(name = "destacado", nullable = false)
    private Boolean destacado;

    @Column(name = "hotel", length = 150)
    private String hotel;

    @Column(name = "transporte", length = 120)
    private String transporte;

    @Column(name = "guia", length = 120)
    private String guia;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "itinerario", nullable = false, columnDefinition = "TEXT")
    private String itinerario;

    @Column(name = "incluye", columnDefinition = "TEXT")
    private String incluye;

    @Column(name = "no_incluye", columnDefinition = "TEXT")
    private String noIncluye;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "ultima_modificacion", nullable = false)
    private LocalDateTime ultimaModificacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        if (this.activo == null) this.activo = true;
        if (this.destacado == null) this.destacado = false;
        if (this.calificacionPromedio == null) this.calificacionPromedio = 5.0;
    }

    @PreUpdate
    protected void onUpdate() {
        this.ultimaModificacion = LocalDateTime.now();
    }
}
