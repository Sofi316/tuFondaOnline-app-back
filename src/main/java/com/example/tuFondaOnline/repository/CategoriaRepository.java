package com.example.tuFondaOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tuFondaOnline.model.Categoria;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
