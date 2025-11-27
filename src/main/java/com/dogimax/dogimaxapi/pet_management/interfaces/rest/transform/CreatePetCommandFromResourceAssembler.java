package com.dogimax.dogimaxapi.pet_management.interfaces.rest.transform;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;
import com.dogimax.dogimaxapi.pet_management.domain.model.commands.CreatePetCommand;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.CreatePetResource;

/**
 * Assembler to convert a CreatePetResource to a CreatePetCommand
 */
public class CreatePetCommandFromResourceAssembler {
    /**
     * Converts a CreatePetResource to a CreatePetCommand
     * @param resource the CreatePetResource
     * @return the CreatePetCommand
     */
    public static CreatePetCommand toCommandFromResource(CreatePetResource resource) {
        Pet.Gender gender = null;
        if (resource.gender() != null) {
            try {
                gender = Pet.Gender.valueOf(resource.gender().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Si el valor no es válido, se deja como null
            }
        }
        
        return new CreatePetCommand(
                resource.name(),
                resource.species(),
                resource.breed(),
                resource.birthDate(),
                gender,
                resource.weight(),
                resource.color(),
                resource.isNeutered(),
                resource.observations(),
                resource.photo(),
                resource.userId()
        );
    }
}

