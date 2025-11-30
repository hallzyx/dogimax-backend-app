package com.dogimax.dogimaxapi.appointments.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Appointment aggregate root.
 * Represents a veterinary appointment (cita) in the system.
 */
@Entity
@Table(name = "appointments")
@Getter
@Setter
public class Appointment extends AuditableAbstractAggregateRoot<Appointment> {

    @Column(name = "mascota_id", nullable = false)
    private Long mascotaId;

    @Column(name = "veterinary_id", nullable = false)
    private Long veterinaryId; // ID del usuario veterinario (User con rol veterinary)

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "motivo", nullable = false, length = 500)
    private String motivo;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;

    @Column(name = "veterinary_status", nullable = false, length = 50)
    private String veterinaryStatus;

    /**
     * Default constructor
     */
    public Appointment() {
        this.estado = "Programada";
        this.notas = "";
        this.veterinaryStatus = "PENDING";
    }

    /**
     * Constructor with all required fields
     * @param mascotaId The pet ID
     * @param veterinaryId The veterinary user ID (User with role veterinary)
     * @param fechaHora The appointment date and time
     * @param motivo The reason for the appointment
     * @param estado The appointment status
     * @param notas Additional notes
     */
    public Appointment(Long mascotaId, Long veterinaryId, LocalDateTime fechaHora, 
                      String motivo, String estado, String notas) {
        this.mascotaId = mascotaId;
        this.veterinaryId = veterinaryId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = estado != null && !estado.isEmpty() ? estado : "Programada";
        this.notas = notas != null ? notas : "";
        this.veterinaryStatus = "PENDING";
    }
}
