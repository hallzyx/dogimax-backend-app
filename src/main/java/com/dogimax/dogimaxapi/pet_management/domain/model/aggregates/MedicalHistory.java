package com.dogimax.dogimaxapi.pet_management.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * MedicalHistory aggregate root
 * Represents a medical history record for a pet in the system
 */
@Entity
@Table(name = "medical_histories")
@Getter
@Setter
public class MedicalHistory extends AuditableAbstractAggregateRoot<MedicalHistory> {

    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @NotNull
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "record_type", nullable = false, length = 50)
    private RecordType recordType;

    @NotBlank
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "veterinarian", length = 200)
    private String veterinarian;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "files", columnDefinition = "TEXT")
    private String files;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "next_appointment")
    private LocalDateTime nextAppointment;

    /**
     * Enum for medical record types
     */
    public enum RecordType {
        VACCINATION,
        CONSULTATION,
        SURGERY,
        EXAM,
        TREATMENT
    }

    /**
     * Default constructor
     */
    public MedicalHistory() {
        super();
        this.registrationDate = LocalDateTime.now();
    }

    /**
     * Constructor with all required fields
     * @param petId The pet ID
     * @param registrationDate The registration date
     * @param recordType The record type
     * @param description The description
     */
    public MedicalHistory(Long petId, LocalDateTime registrationDate, RecordType recordType, String description) {
        this();
        this.petId = petId;
        this.registrationDate = registrationDate;
        this.recordType = recordType;
        this.description = description;
    }

    /**
     * Constructor with all fields
     * @param petId The pet ID
     * @param registrationDate The registration date
     * @param recordType The record type
     * @param description The description
     * @param veterinarian The veterinarian name
     * @param observations Additional observations
     * @param files File paths (comma-separated)
     * @param cost Cost of the service
     * @param nextAppointment Next appointment date
     */
    public MedicalHistory(Long petId, LocalDateTime registrationDate, RecordType recordType, 
                          String description, String veterinarian, String observations, 
                          String files, Double cost, LocalDateTime nextAppointment) {
        this(petId, registrationDate, recordType, description);
        this.veterinarian = veterinarian;
        this.observations = observations;
        this.files = files;
        this.cost = cost;
        this.nextAppointment = nextAppointment;
    }
}
