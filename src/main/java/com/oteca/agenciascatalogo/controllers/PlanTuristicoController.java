package com.oteca.agenciascatalogo.controllers;

import com.oteca.agenciascatalogo.models.dtos.ApiResponse;
import com.oteca.agenciascatalogo.models.dtos.PlanTuristicoRequestDTO;
import com.oteca.agenciascatalogo.models.dtos.PlanTuristicoResponseDTO;
import com.oteca.agenciascatalogo.services.PlanTuristicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/catalogo/planes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "3. Planes Turísticos (Catálogo)", description = "CRUD y filtros de búsqueda del catálogo por municipio, categoría, agencia, precio y duración (RF4, RF7, RF17)")
public class PlanTuristicoController {

    private final PlanTuristicoService planService;

    @GetMapping
    @Operation(summary = "Buscar y filtrar planes del catálogo (RF7)", description = "Permite filtrar simultáneamente por municipio, categoría, agencia, precio máximo y duración en días.")
    public ResponseEntity<ApiResponse<List<PlanTuristicoResponseDTO>>> buscarPlanes(
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Long agenciaId,
            @RequestParam(required = false) BigDecimal precioMaximo,
            @RequestParam(required = false) Integer duracionDias) {
        List<PlanTuristicoResponseDTO> planes = planService.buscarPlanes(
                municipio, categoriaId, agenciaId, precioMaximo, duracionDias
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(planes));
    }

    @GetMapping("/destacados")
    @Operation(summary = "Listar planes destacados (RF17)", description = "Devuelve los planes turísticos marcados como destacados para la página principal.")
    public ResponseEntity<ApiResponse<List<PlanTuristicoResponseDTO>>> listarDestacados() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(planService.listarDestacados()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de plan turístico por ID", description = "Obtiene toda la información de un plan: itinerario, qué incluye, hotel, transporte y guía.")
    public ResponseEntity<ApiResponse<PlanTuristicoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(planService.obtenerPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Publicar nuevo plan en el catálogo (RF4)", description = "Registra un nuevo plan turístico validando precio positivo, cupo, agencia y categoría existente.")
    public ResponseEntity<ApiResponse<PlanTuristicoResponseDTO>> crearPlan(
            @Valid @RequestBody PlanTuristicoRequestDTO formulario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(planService.crearPlan(formulario)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar plan turístico (RF4)", description = "Actualiza los datos, tarifas, cupos o itinerario de un plan existente.")
    public ResponseEntity<ApiResponse<PlanTuristicoResponseDTO>> actualizarPlan(
            @PathVariable Long id,
            @Valid @RequestBody PlanTuristicoRequestDTO formulario) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(planService.actualizarPlan(id, formulario)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar plan turístico (RF4)", description = "Elimina un plan turístico del catálogo por su ID.")
    public ResponseEntity<ApiResponse<String>> eliminarPlan(@PathVariable Long id) {
        planService.eliminarPlan(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Plan turístico con ID " + id + " eliminado exitosamente."));
    }
}
