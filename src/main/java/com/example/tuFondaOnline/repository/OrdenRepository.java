package com.example.tuFondaOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tuFondaOnline.model.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    
}
