package com.dogimax.dogimaxapi.pet_management.domain.model.commands;

/**
 * Command to delete a pet
 * @param id the pet's id
 */
public record DeletePetCommand(Long id) {
}

