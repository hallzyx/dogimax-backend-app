package com.dogimax.dogimaxapi.pet_management.interfaces.rest.transform;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.PetResource;

import java.time.ZoneId;

/**
 * Assembler to convert a Pet entity to a PetResource
 */
public class PetResourceFromEntityAssembler {
    /**
     * Converts a Pet entity to a PetResource
     * @param entity the Pet entity
     * @return the PetResource
     */
    public static PetResource toResourceFromEntity(Pet entity) {
        return new PetResource(
                entity.getId(),
                entity.getName(),
                entity.getSpecies(),
                entity.getBreed(),
                entity.getBirthDate(),
                entity.getGender() != null ? entity.getGender().name() : null,
                entity.getWeight(),
                entity.getColor(),
                entity.getIsNeutered(),
                entity.getObservations(),
                entity.getPhoto(),
                entity.getUserId(),
                entity.getIsActive(),
                entity.getCreatedAt() != null 
                    ? entity.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                    : null
        );
    }
}

