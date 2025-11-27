package com.dogimax.dogimaxapi.pet_management.domain.model.commands;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;

import java.time.LocalDate;

/**
 * Command to update a pet
 * @param id the pet's id
 * @param name the pet's name
 * @param species the pet's species
 * @param breed the pet's breed
 * @param birthDate the pet's birth date
 * @param gender the pet's gender
 * @param weight the pet's weight
 * @param color the pet's color
 * @param isNeutered whether the pet is sterilized
 * @param observations additional observations
 * @param photo the pet's photo URL
 */
public record UpdatePetCommand(
        Long id,
        String name,
        String species,
        String breed,
        LocalDate birthDate,
        Pet.Gender gender,
        Double weight,
        String color,
        Boolean isNeutered,
        String observations,
        String photo
) {
}

