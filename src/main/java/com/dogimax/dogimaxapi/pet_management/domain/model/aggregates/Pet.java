package com.dogimax.dogimaxapi.pet_management.domain.model.aggregates;

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
 * Pet aggregate root
 * This class represents the aggregate root for the Pet entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet extends AuditableAbstractAggregateRoot<Pet> {

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Size(max = 50)
    @Column(name = "species", nullable = false)
    private String species;

    @Size(max = 50)
    @Column(name = "breed")
    private String breed;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Positive
    @Column(name = "weight")
    private Double weight;

    @Size(max = 50)
    @Column(name = "color")
    private String color;

    @Column(name = "is_neutered")
    private Boolean isNeutered;

    @Size(max = 500)
    @Column(name = "observations", length = 500)
    private String observations;

    @Size(max = 500)
    @Column(name = "photo", length = 500)
    private String photo;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public enum Gender {
        MALE,
        FEMALE
    }

    public Pet() {
        this.isActive = true;
        this.isNeutered = false;
    }

    public Pet(String name, String species, String breed, LocalDate birthDate, 
               Gender gender, Double weight, String color, Boolean isNeutered,
               String observations, String photo, Long userId) {
        this();
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.color = color;
        this.isNeutered = isNeutered;
        this.observations = observations;
        this.photo = photo;
        this.userId = userId;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }
}

