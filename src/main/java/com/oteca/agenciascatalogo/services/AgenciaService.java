package com.oteca.agenciascatalogo.services;

import com.oteca.agenciascatalogo.exceptions.DuplicateResourceException;
import com.oteca.agenciascatalogo.exceptions.ResourceNotFoundException;
import com.oteca.agenciascatalogo.models.dtos.AgenciaContactoUpdateDTO;
import com.oteca.agenciascatalogo.models.dtos.AgenciaRegistroDTO;
import com.oteca.agenciascatalogo.models.dtos.AgenciaResponseDTO;
import com.oteca.agenciascatalogo.models.entities.Agencia;
import com.oteca.agenciascatalogo.models.entities.EstadoAgencia;
import com.oteca.agenciascatalogo.repositories.AgenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenciaService {

    private final AgenciaRepository agenciaRepository;

    @Transactional(readOnly = true)
    public List<AgenciaResponseDTO> listarTodas(String ciudad) {
        List<Agencia> agencias = (ciudad != null && !ciudad.isBlank())
                ? agenciaRepository.findByCiudadIgnoreCase(ciudad.trim())
                : agenciaRepository.findAll();

        return agencias.stream()
                .map(this::mapearAResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public AgenciaResponseDTO obtenerPorId(Long id) {
        Agencia agencia = agenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La agencia turística con ID " + id + " no fue encontrada en el sistema."
                ));
        return mapearAResponseDTO(agencia);
    }

    @Transactional
    public AgenciaResponseDTO registrarAgencia(AgenciaRegistroDTO dto) {
        if (agenciaRepository.existsByNit(dto.getNit())) {
            throw new DuplicateResourceException("nit", "Ya existe una agencia registrada con el NIT " + dto.getNit());
        }
        if (agenciaRepository.existsByRnt(dto.getRnt())) {
            throw new DuplicateResourceException("rnt", "Ya existe una agencia registrada con el RNT " + dto.getRnt());
        }
        if (agenciaRepository.existsByCorreoIgnoreCase(dto.getCorreo())) {
            throw new DuplicateResourceException("correo", "El correo electrónico " + dto.getCorreo() + " ya se encuentra registrado.");
        }

        Agencia nuevaAgencia = Agencia.builder()
                .nombre(dto.getNombre())
                .nit(dto.getNit())
                .rnt(dto.getRnt())
                .ciudad(dto.getCiudad())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo().toLowerCase())
                .sitioWeb(dto.getSitioWeb())
                .redesSociales(dto.getRedesSociales())
                .descripcion(dto.getDescripcion())
                .anioFundacion(dto.getAnioFundacion())
                .estado(EstadoAgencia.PENDIENTE_APROBACION)
                .build();

        Agencia guardada = agenciaRepository.save(nuevaAgencia);
        return mapearAResponseDTO(guardada);
    }

    @Transactional
    public AgenciaResponseDTO actualizarContacto(Long id, AgenciaContactoUpdateDTO dto) {
        Agencia agencia = agenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se puede actualizar: la agencia con ID " + id + " no existe."
                ));

        if (!agencia.getCorreo().equalsIgnoreCase(dto.getCorreo())
                && agenciaRepository.existsByCorreoIgnoreCase(dto.getCorreo())) {
            throw new DuplicateResourceException("correo", "El correo electrónico " + dto.getCorreo() + " ya pertenece a otra agencia.");
        }

        agencia.setDireccion(dto.getDireccion());
        agencia.setTelefono(dto.getTelefono());
        agencia.setCorreo(dto.getCorreo().toLowerCase());
        agencia.setSitioWeb(dto.getSitioWeb());
        agencia.setRedesSociales(dto.getRedesSociales());
        if (dto.getDescripcion() != null && !dto.getDescripcion().isBlank()) {
            agencia.setDescripcion(dto.getDescripcion());
        }

        Agencia actualizada = agenciaRepository.save(agencia);
        return mapearAResponseDTO(actualizada);
    }

    @Transactional
    public AgenciaResponseDTO cambiarEstadoRnt(Long id, EstadoAgencia nuevoEstado) {
        Agencia agencia = agenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La agencia con ID " + id + " no existe."
                ));
        agencia.setEstado(nuevoEstado);
        return mapearAResponseDTO(agenciaRepository.save(agencia));
    }

    @Transactional
    public void eliminarAgencia(Long id) {
        if (!agenciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: la agencia con ID " + id + " no existe.");
        }
        agenciaRepository.deleteById(id);
    }

    private AgenciaResponseDTO mapearAResponseDTO(Agencia a) {
        return AgenciaResponseDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .nit(a.getNit())
                .rnt(a.getRnt())
                .ciudad(a.getCiudad())
                .direccion(a.getDireccion())
                .telefono(a.getTelefono())
                .correo(a.getCorreo())
                .sitioWeb(a.getSitioWeb())
                .redesSociales(a.getRedesSociales())
                .descripcion(a.getDescripcion())
                .anioFundacion(a.getAnioFundacion())
                .estado(a.getEstado())
                .fechaRegistro(a.getFechaRegistro())
                .build();
    }
}
