package com.example.tuFondaOnline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_orden")
    private Long id;

    @JoinColumn(name="id_orden", nullable = false)
    private Orden orden;

    @JoinColumn(name="id_producto", nullable = false)
    private Producto producto;

    @Column(name="cantidad", nullable = false)
    private Integer cantidad;

    @Column(name="precio_unitario", nullable = false)
    private Integer precio;

}
