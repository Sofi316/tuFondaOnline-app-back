package com.example.tuFondaOnline.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long id;

    @Column(nullable = false, length=13, unique=true)
    private String rut;

    @Column(nullable = false, length=40)
    private String nombre;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String rol; 

    @Column(nullable = false, length=120)
    private String direccion;

    @Column(nullable = false, length=100)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_comuna")
    private Comuna comuna;
}
