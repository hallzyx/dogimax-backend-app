package com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Pet resource
 * @param id the pet id
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
 * @param isActive the pet's active status
 * @param fechaRegistro the registration date
 */
public record PetResource(
        Long id,
        String name,
        String species,
        String breed,
        LocalDate birthDate,
        String gender,
        Double weight,
        String color,
        Boolean isNeutered,
        String observations,
        String photo,
        Long userId,
        Boolean isActive,
        LocalDateTime fechaRegistro
) {
}

