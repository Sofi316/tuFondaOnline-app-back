package com.example.tuFondaOnline.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist; 
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orden") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long id;

    @Column(name = "fecha_orden", nullable = false, updatable = false)
    private LocalDateTime fechaOrden; 

    @Column(name = "estado", nullable = false)
    private String estado; 

    @Column(name = "total", nullable = false)
    private Integer total;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false) 
    private Usuario usuario;


    @PrePersist
    public void prePersist() {
       
        this.fechaOrden = LocalDateTime.now();
        
    
        if (this.estado == null) {
            this.estado = "PENDIENTE";
        }
    }
}