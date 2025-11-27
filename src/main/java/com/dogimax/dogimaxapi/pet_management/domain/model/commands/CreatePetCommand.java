package com.dogimax.dogimaxapi.pet_management.domain.model.commands;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;

import java.time.LocalDate;

/**
 * Command to create a pet
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
 * @param userId the owner's user id
 */
public record CreatePetCommand(
        String name,
        String species,
        String breed,
        LocalDate birthDate,
        Pet.Gender gender,
        Double weight,
        String color,
        Boolean isNeutered,
        String observations,
        String photo,
        Long userId
) {
}

