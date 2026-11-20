package com.oteca.agenciascatalogo.repositories;

import com.oteca.agenciascatalogo.models.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}
