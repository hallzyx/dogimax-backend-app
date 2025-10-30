package com.dogimax.dogimaxapi.iam.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AuditableAbstractAggregateRoot<User> {

    @NotBlank
    @Size(max = 100)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank
    @Size(max = 100)
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotBlank
    @Email
    @Size(max = 150)
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Column(name = "rol", nullable = false)
    private String rol;

    public User() {
        this.fechaRegistro = LocalDateTime.now();
        this.rol = "USER"; // Default role
    }

    public User(String nombre, String apellido, String email, String telefono, String password, String rol) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.rol = rol != null ? rol : "USER";
    }
}
