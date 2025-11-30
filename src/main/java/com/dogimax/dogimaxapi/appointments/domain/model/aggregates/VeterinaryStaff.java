package com.dogimax.dogimaxapi.appointments.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * VeterinaryStaff aggregate root.
 * Represents the many-to-many relationship between veterinary users and veterinary clinics.
 * A veterinarian can work at multiple clinics, and a clinic can have multiple veterinarians.
 */
@Entity
@Table(name = "veterinary_staff", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "veterinary_id"}))
@Getter
@Setter
public class VeterinaryStaff extends AuditableAbstractAggregateRoot<VeterinaryStaff> {

    @Column(name = "user_id", nullable = false)
    private Long userId; // ID del usuario con rol veterinary

    @Column(name = "veterinary_id", nullable = false)
    private Long veterinaryId; // ID de la clínica veterinaria

    @Column(name = "specialty", length = 200)
    private String specialty; // Especialidad del veterinario en esta clínica

    @Column(name = "is_active", nullable = false)
    private Boolean isActive; // Si el veterinario está activo en esta clínica

    /**
     * Default constructor
     */
    public VeterinaryStaff() {
        this.isActive = true;
    }

    /**
     * Constructor with required fields
     * @param userId The veterinary user ID
     * @param veterinaryId The veterinary clinic ID
     */
    public VeterinaryStaff(Long userId, Long veterinaryId) {
        this.userId = userId;
        this.veterinaryId = veterinaryId;
        this.isActive = true;
    }

    /**
     * Constructor with all fields
     * @param userId The veterinary user ID
     * @param veterinaryId The veterinary clinic ID
     * @param specialty The veterinarian's specialty
     * @param isActive Whether the veterinarian is active at this clinic
     */
    public VeterinaryStaff(Long userId, Long veterinaryId, String specialty, Boolean isActive) {
        this.userId = userId;
        this.veterinaryId = veterinaryId;
        this.specialty = specialty;
        this.isActive = isActive != null ? isActive : true;
    }
}
