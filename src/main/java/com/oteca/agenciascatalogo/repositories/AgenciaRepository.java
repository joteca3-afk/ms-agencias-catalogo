package com.oteca.agenciascatalogo.repositories;

import com.oteca.agenciascatalogo.models.entities.Agencia;
import com.oteca.agenciascatalogo.models.entities.EstadoAgencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long> {

    boolean existsByNit(String nit);

    boolean existsByRnt(String rnt);

    boolean existsByCorreoIgnoreCase(String correo);

    List<Agencia> findByCiudadIgnoreCase(String ciudad);

    List<Agencia> findByEstado(EstadoAgencia estado);
}
