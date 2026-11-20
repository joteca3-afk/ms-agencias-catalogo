package com.oteca.agenciascatalogo.services;

import com.oteca.agenciascatalogo.exceptions.ResourceNotFoundException;
import com.oteca.agenciascatalogo.models.dtos.PlanTuristicoRequestDTO;
import com.oteca.agenciascatalogo.models.dtos.PlanTuristicoResponseDTO;
import com.oteca.agenciascatalogo.models.entities.Agencia;
import com.oteca.agenciascatalogo.models.entities.Categoria;
import com.oteca.agenciascatalogo.models.entities.PlanTuristico;
import com.oteca.agenciascatalogo.repositories.AgenciaRepository;
import com.oteca.agenciascatalogo.repositories.CategoriaRepository;
import com.oteca.agenciascatalogo.repositories.PlanTuristicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanTuristicoService {

    private final PlanTuristicoRepository planRepository;
    private final AgenciaRepository agenciaRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<PlanTuristicoResponseDTO> buscarPlanes(
            String municipio,
            Long categoriaId,
            Long agenciaId,
            BigDecimal precioMaximo,
            Integer duracionDias) {

        List<PlanTuristico> planes = planRepository.findAll();

        return planes.stream()
                .filter(p -> municipio == null || municipio.isBlank()
                        || p.getMunicipioDestino().toLowerCase().contains(municipio.trim().toLowerCase()))
                .filter(p -> categoriaId == null || p.getCategoria().getId().equals(categoriaId))
                .filter(p -> agenciaId == null || p.getAgencia().getId().equals(agenciaId))
                .filter(p -> precioMaximo == null || p.getPrecio().compareTo(precioMaximo) <= 0)
                .filter(p -> duracionDias == null || p.getDuracionDias().equals(duracionDias))
                .map(this::mapearADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PlanTuristicoResponseDTO> listarDestacados() {
        return planRepository.findByDestacadoTrue().stream()
                .map(this::mapearADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlanTuristicoResponseDTO obtenerPorId(Long id) {
        PlanTuristico plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "El plan turístico con ID " + id + " no fue encontrado en el catálogo."
                ));
        return mapearADTO(plan);
    }

    @Transactional
    public PlanTuristicoResponseDTO crearPlan(PlanTuristicoRequestDTO dto) {
        Agencia agencia = agenciaRepository.findById(dto.getAgenciaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se puede crear el plan: la agencia con ID " + dto.getAgenciaId() + " no existe."
                ));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se puede crear el plan: la categoría de turismo con ID " + dto.getCategoriaId() + " no existe."
                ));

        PlanTuristico plan = PlanTuristico.builder()
                .nombre(dto.getNombre())
                .agencia(agencia)
                .municipioDestino(dto.getMunicipioDestino())
                .categoria(categoria)
                .precio(dto.getPrecio())
                .duracionDias(dto.getDuracionDias())
                .cupoMaximo(dto.getCupoMaximo())
                .calificacionPromedio(5.0)
                .destacado(dto.getDestacado() != null ? dto.getDestacado() : false)
                .hotel(dto.getHotel())
                .transporte(dto.getTransporte())
                .guia(dto.getGuia())
                .descripcion(dto.getDescripcion())
                .itinerario(dto.getItinerario())
                .incluye(dto.getIncluye())
                .noIncluye(dto.getNoIncluye())
                .activo(true)
                .build();

        return mapearADTO(planRepository.save(plan));
    }

    @Transactional
    public PlanTuristicoResponseDTO actualizarPlan(Long id, PlanTuristicoRequestDTO dto) {
        PlanTuristico plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se puede actualizar: el plan turístico con ID " + id + " no existe."
                ));

        Agencia agencia = agenciaRepository.findById(dto.getAgenciaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La agencia con ID " + dto.getAgenciaId() + " no existe."
                ));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La categoría de turismo con ID " + dto.getCategoriaId() + " no existe."
                ));

        plan.setNombre(dto.getNombre());
        plan.setAgencia(agencia);
        plan.setMunicipioDestino(dto.getMunicipioDestino());
        plan.setCategoria(categoria);
        plan.setPrecio(dto.getPrecio());
        plan.setDuracionDias(dto.getDuracionDias());
        plan.setCupoMaximo(dto.getCupoMaximo());
        if (dto.getDestacado() != null) {
            plan.setDestacado(dto.getDestacado());
        }
        plan.setHotel(dto.getHotel());
        plan.setTransporte(dto.getTransporte());
        plan.setGuia(dto.getGuia());
        plan.setDescripcion(dto.getDescripcion());
        plan.setItinerario(dto.getItinerario());
        plan.setIncluye(dto.getIncluye());
        plan.setNoIncluye(dto.getNoIncluye());

        return mapearADTO(planRepository.save(plan));
    }

    @Transactional
    public void eliminarPlan(Long id) {
        if (!planRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: el plan turístico con ID " + id + " no existe.");
        }
        planRepository.deleteById(id);
    }

    private PlanTuristicoResponseDTO mapearADTO(PlanTuristico p) {
        return PlanTuristicoResponseDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .agenciaId(p.getAgencia().getId())
                .nombreAgencia(p.getAgencia().getNombre())
                .municipioDestino(p.getMunicipioDestino())
                .categoriaId(p.getCategoria().getId())
                .nombreCategoria(p.getCategoria().getNombre())
                .precio(p.getPrecio())
                .duracionDias(p.getDuracionDias())
                .cupoMaximo(p.getCupoMaximo())
                .calificacionPromedio(p.getCalificacionPromedio())
                .destacado(p.getDestacado())
                .hotel(p.getHotel())
                .transporte(p.getTransporte())
                .guia(p.getGuia())
                .descripcion(p.getDescripcion())
                .itinerario(p.getItinerario())
                .incluye(p.getIncluye())
                .noIncluye(p.getNoIncluye())
                .activo(p.getActivo())
                .fechaCreacion(p.getFechaCreacion())
                .build();
    }
}
