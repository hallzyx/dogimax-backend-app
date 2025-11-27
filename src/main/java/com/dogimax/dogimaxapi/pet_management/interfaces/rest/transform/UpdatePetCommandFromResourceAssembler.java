package com.dogimax.dogimaxapi.pet_management.interfaces.rest.transform;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;
import com.dogimax.dogimaxapi.pet_management.domain.model.commands.UpdatePetCommand;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.UpdatePetResource;

/**
 * Assembler to convert an UpdatePetResource to an UpdatePetCommand
 */
public class UpdatePetCommandFromResourceAssembler {
    /**
     * Converts an UpdatePetResource to an UpdatePetCommand
     * @param id the pet id
     * @param resource the UpdatePetResource
     * @return the UpdatePetCommand
     */
    public static UpdatePetCommand toCommandFromResource(Long id, UpdatePetResource resource) {
        Pet.Gender gender = null;
        if (resource.gender() != null) {
            try {
                gender = Pet.Gender.valueOf(resource.gender().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Si el valor no es válido, se deja como null
            }
        }
        
        return new UpdatePetCommand(
                id,
                resource.name(),
                resource.species(),
                resource.breed(),
                resource.birthDate(),
                gender,
                resource.weight(),
                resource.color(),
                resource.isNeutered(),
                resource.observations(),
                resource.photo()
        );
    }
}

