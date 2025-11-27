package com.dogimax.dogimaxapi.pet_management.domain.services;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;
import com.dogimax.dogimaxapi.pet_management.domain.model.commands.CreatePetCommand;
import com.dogimax.dogimaxapi.pet_management.domain.model.commands.DeletePetCommand;
import com.dogimax.dogimaxapi.pet_management.domain.model.commands.UpdatePetCommand;

import java.util.Optional;

/**
 * Pet command service
 * <p>
 *     This interface represents the service to handle pet commands.
 * </p>
 */
public interface PetCommandService {
    /**
     * Handle create pet command
     * @param command the {@link CreatePetCommand} command
     * @return the created {@link Pet} entity
     */
    Pet handle(CreatePetCommand command);

    /**
     * Handle update pet command
     * @param command the {@link UpdatePetCommand} command
     * @return an {@link Optional} of {@link Pet} entity
     */
    Optional<Pet> handle(UpdatePetCommand command);

    /**
     * Handle delete pet command
     * @param command the {@link DeletePetCommand} command
     */
    void handle(DeletePetCommand command);
}

