package com.oteca.agenciascatalogo.controllers;

import com.oteca.agenciascatalogo.models.dtos.AgenciaContactoUpdateDTO;
import com.oteca.agenciascatalogo.models.dtos.AgenciaRegistroDTO;
import com.oteca.agenciascatalogo.models.dtos.AgenciaResponseDTO;
import com.oteca.agenciascatalogo.models.dtos.ApiResponse;
import com.oteca.agenciascatalogo.models.entities.EstadoAgencia;
import com.oteca.agenciascatalogo.services.AgenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "1. Agencias Turísticas", description = "Registro, consulta, actualización de contacto y verificación RNT de agencias (RF1, RF3)")
public class AgenciaController {

    private final AgenciaService agenciaService;

    @GetMapping
    @Operation(summary = "Listar agencias", description = "Obtiene todas las agencias registradas o filtra opcionalmente por ciudad del Huila (ej. Neiva, Pitalito, Garzón).")
    public ResponseEntity<ApiResponse<List<AgenciaResponseDTO>>> listarAgencias(
            @RequestParam(required = false) String ciudad) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(agenciaService.listarTodas(ciudad)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener agencia por ID", description = "Busca el perfil detallado de una agencia por su identificador único.")
    public ResponseEntity<ApiResponse<AgenciaResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(agenciaService.obtenerPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Registrar nueva agencia (RF1)", description = "Registra una agencia turística con estado inicial PENDIENTE_APROBACION validando NIT, RNT y correo.")
    public ResponseEntity<ApiResponse<AgenciaResponseDTO>> registrarAgencia(
            @Valid @RequestBody AgenciaRegistroDTO formulario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(agenciaService.registrarAgencia(formulario)));
    }

    @PutMapping("/{id}/contacto")
    @Operation(summary = "Actualizar contacto y redes sociales (RF3)", description = "Actualiza dirección, teléfono, correo, sitio web y redes sociales de una agencia.")
    public ResponseEntity<ApiResponse<AgenciaResponseDTO>> actualizarContacto(
            @PathVariable Long id,
            @Valid @RequestBody AgenciaContactoUpdateDTO formulario) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(agenciaService.actualizarContacto(id, formulario)));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Aprobar o rechazar RNT de agencia (RF1)", description = "Actualiza el estado de verificación RNT de la agencia (APROBADA, RECHAZADA, PENDIENTE_APROBACION).")
    public ResponseEntity<ApiResponse<AgenciaResponseDTO>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoAgencia estado) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(agenciaService.cambiarEstadoRnt(id, estado)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar agencia", description = "Elimina una agencia del registro por su ID.")
    public ResponseEntity<ApiResponse<String>> eliminarAgencia(@PathVariable Long id) {
        agenciaService.eliminarAgencia(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("Agencia con ID " + id + " eliminada exitosamente."));
    }
}
