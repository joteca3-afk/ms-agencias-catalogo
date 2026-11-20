package com.oteca.agenciascatalogo.repositories;

import com.oteca.agenciascatalogo.models.entities.PlanTuristico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanTuristicoRepository extends JpaRepository<PlanTuristico, Long> {

    List<PlanTuristico> findByDestacadoTrue();

    List<PlanTuristico> findByAgenciaId(Long agenciaId);

    List<PlanTuristico> findByCategoriaId(Long categoriaId);
}
