package com.example.tuFondaOnline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Producto {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Long id;

    @Column(name="nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name="descripcion", length = 255)
    private String descripcion;

    @Column(name="precio", nullable = false)
    private Integer precio;

    @Column(name="stock", nullable = false)
    private int stock;

    @Column(name="img")
    private String img;

    @Column(name="en_oferta", nullable = false)
    private Boolean enOferta;

    @Column(name="precio_oferta")
    private Integer precioOferta;

    @ManyToOne
    @JoinColumn(name="id_categoria", nullable = false)
    private Categoria categoria;
   
}
