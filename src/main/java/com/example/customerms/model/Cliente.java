package com.example.customerms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@Table(name = "clientes")
public class Cliente {
    @Id
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe tener exactamente 8 dígitos")
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @Email(message = "Debe proporcionar un correo electrónico válido", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @Column(name = "email", nullable = false)
    private String email;

}
