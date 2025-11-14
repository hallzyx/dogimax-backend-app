package com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Mascota aggregate root
 * This class represents the aggregate root for the Mascota entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
@Table(name = "mascotas")
public class Mascota extends AuditableAbstractAggregateRoot<Mascota> {

    @NotBlank
    @Size(max = 100)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank
    @Size(max = 50)
    @Column(name = "especie", nullable = false)
    private String especie;

    @Size(max = 50)
    @Column(name = "raza")
    private String raza;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;

    @Positive
    @Column(name = "peso")
    private Double peso;

    @Size(max = 20)
    @Column(name = "unidad_peso")
    private String unidadPeso;

    @Size(max = 500)
    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Size(max = 500)
    @Column(name = "foto_url", length = 500)
    private String fotoUrl;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "activa", nullable = false)
    private Boolean activa;

    public enum Sexo {
        MACHO,
        HEMBRA
    }

    public Mascota() {
        this.activa = true;
    }

    public Mascota(String nombre, String especie, String raza, LocalDate fechaNacimiento, 
                   Sexo sexo, Double peso, String unidadPeso, String descripcion, 
                   String fotoUrl, Long userId) {
        this();
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.peso = peso;
        this.unidadPeso = unidadPeso;
        this.descripcion = descripcion;
        this.fotoUrl = fotoUrl;
        this.userId = userId;
    }

    public void desactivar() {
        this.activa = false;
    }

    public void activar() {
        this.activa = true;
    }
}

