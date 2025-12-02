package com.example.tuFondaOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tuFondaOnline.model.DetalleOrden;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    List<DetalleOrden> findByOrden_Usuario_Email(String email);
    List<DetalleOrden> findByOrdenId(Long idOrden);

}
