package com.example.tuFondaOnline.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.tuFondaOnline.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    
 }