package com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * HistorialMedico aggregate root
 * Represents a medical history record for a pet in the system
 */
@Entity
@Table(name = "historial_medico")
@Getter
@Setter
public class HistorialMedico extends AuditableAbstractAggregateRoot<HistorialMedico> {

    @NotNull
    @Column(name = "mascota_id", nullable = false)
    private Long mascotaId;

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_registro", nullable = false, length = 50)
    private TipoRegistro tipoRegistro;

    @NotBlank
    @Column(name = "descripcion", nullable = false, length = 1000)
    private String descripcion;

    @Column(name = "veterinario", length = 200)
    private String veterinario;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "archivos", columnDefinition = "TEXT")
    private String archivos;

    @Column(name = "costo")
    private Double costo;

    @Column(name = "proxima_cita")
    private LocalDateTime proximaCita;

    /**
     * Enum for medical record types
     */
    public enum TipoRegistro {
        VACUNA,
        CONSULTA,
        CIRUGIA,
        EXAMEN,
        TRATAMIENTO
    }

    /**
     * Default constructor
     */
    public HistorialMedico() {
        super();
        this.fechaRegistro = LocalDateTime.now();
    }

    /**
     * Constructor with all required fields
     * @param mascotaId The pet ID
     * @param fechaRegistro The registration date
     * @param tipoRegistro The record type
     * @param descripcion The description
     */
    public HistorialMedico(Long mascotaId, LocalDateTime fechaRegistro, TipoRegistro tipoRegistro, String descripcion) {
        this();
        this.mascotaId = mascotaId;
        this.fechaRegistro = fechaRegistro;
        this.tipoRegistro = tipoRegistro;
        this.descripcion = descripcion;
    }

    /**
     * Constructor with all fields
     * @param mascotaId The pet ID
     * @param fechaRegistro The registration date
     * @param tipoRegistro The record type
     * @param descripcion The description
     * @param veterinario The veterinarian name
     * @param observaciones Additional observations
     * @param archivos File paths (comma-separated)
     * @param costo Cost of the service
     * @param proximaCita Next appointment date
     */
    public HistorialMedico(Long mascotaId, LocalDateTime fechaRegistro, TipoRegistro tipoRegistro, 
                          String descripcion, String veterinario, String observaciones, 
                          String archivos, Double costo, LocalDateTime proximaCita) {
        this(mascotaId, fechaRegistro, tipoRegistro, descripcion);
        this.veterinario = veterinario;
        this.observaciones = observaciones;
        this.archivos = archivos;
        this.costo = costo;
        this.proximaCita = proximaCita;
    }
}
