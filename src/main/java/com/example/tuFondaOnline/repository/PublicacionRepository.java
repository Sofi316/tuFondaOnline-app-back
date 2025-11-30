package com.example.tuFondaOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tuFondaOnline.model.Publicacion;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    
}