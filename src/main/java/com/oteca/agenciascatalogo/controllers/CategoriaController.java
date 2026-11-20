package com.oteca.agenciascatalogo.controllers;

import com.oteca.agenciascatalogo.models.dtos.ApiResponse;
import com.oteca.agenciascatalogo.models.dtos.CategoriaRequestDTO;
import com.oteca.agenciascatalogo.models.dtos.CategoriaResponseDTO;
import com.oteca.agenciascatalogo.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "2. Categorías de Turismo (Catálogo)", description = "Clasificación de planes por tipo de turismo: Aventura, Cultural, Ecológico, Astroturismo (RF6)")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Listar categorías", description = "Obtiene todas las categorías de turismo disponibles.")
    public ResponseEntity<ApiResponse<List<CategoriaResponseDTO>>> listarCategorias() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(categoriaService.listarTodas()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Busca una categoría de turismo por su ID.")
    public ResponseEntity<ApiResponse<CategoriaResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(categoriaService.obtenerPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Crear categoría de turismo", description = "Registra una nueva categoría validando que no esté duplicada.")
    public ResponseEntity<ApiResponse<CategoriaResponseDTO>> crearCategoria(
            @Valid @RequestBody CategoriaRequestDTO formulario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(categoriaService.crearCategoria(formulario)));
    }
}
