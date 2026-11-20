package com.oteca.agenciascatalogo.services;

import com.oteca.agenciascatalogo.exceptions.DuplicateResourceException;
import com.oteca.agenciascatalogo.exceptions.ResourceNotFoundException;
import com.oteca.agenciascatalogo.models.dtos.CategoriaRequestDTO;
import com.oteca.agenciascatalogo.models.dtos.CategoriaResponseDTO;
import com.oteca.agenciascatalogo.models.entities.Categoria;
import com.oteca.agenciascatalogo.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(this::mapearADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La categoría de turismo con ID " + id + " no existe en el catálogo."
                ));
        return mapearADTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto) {
        if (categoriaRepository.existsByNombreIgnoreCase(dto.getNombre().trim())) {
            throw new DuplicateResourceException(
                    "nombre",
                    "Ya existe una categoría de turismo llamada '" + dto.getNombre() + "'."
            );
        }

        Categoria nueva = Categoria.builder()
                .nombre(dto.getNombre().trim())
                .descripcion(dto.getDescripcion())
                .colorHex(dto.getColorHex() != null ? dto.getColorHex() : "#B84B26")
                .activa(true)
                .build();

        return mapearADTO(categoriaRepository.save(nueva));
    }

    private CategoriaResponseDTO mapearADTO(Categoria c) {
        return CategoriaResponseDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .descripcion(c.getDescripcion())
                .colorHex(c.getColorHex())
                .activa(c.getActiva())
                .build();
    }
}
