package com.example.tuFondaOnline.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.tuFondaOnline.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    
    // Búsqueda por nombre 
    Optional<Comuna> findByNombre(String nombre);


    // Spring hace el JOIN automático usando el ID de la región
    List<Comuna> findByRegion_Id(Long idRegion); 
}