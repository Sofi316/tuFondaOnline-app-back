package com.example.tuFondaOnline.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orden")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Orden {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_orden")
    private Long id;

    //@JoinColumn(name="id_usuario", nullable = false)
    //private Usuario usuario;

    @Column(name="fecha_orden", nullable = false)
    private Date fechaOrden;

    @Column(name="estado", nullable = false)
    private String estado;

    @Column(name="total", nullable = false)
    private Integer total;
  
}