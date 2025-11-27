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
@Table(name = "publicacion")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion")
    private Long idPublicacion;
    @Column(nullable=false, length=100)
    private String titulo;
    @Column(nullable=false, length=500)
    private String bajada;
    @Column(nullable=false, length=100)
    private String detalle;
    @Column(nullable=false, length=100)
    private String imagen;
    @Column(nullable=false, length=500)
    private String contenido;
    @Column(nullable=false)
    private Date estado;

    //Faltan las relaciones
    
}
