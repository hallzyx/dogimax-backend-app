package com.dogimax.dogimaxapi.pet_management.domain.services;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;
import com.dogimax.dogimaxapi.pet_management.domain.model.queries.GetAllPetsQuery;
import com.dogimax.dogimaxapi.pet_management.domain.model.queries.GetPetByIdQuery;
import com.dogimax.dogimaxapi.pet_management.domain.model.queries.GetPetsByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Pet query service
 * <p>
 *     This interface represents the service to handle pet queries.
 * </p>
 */
public interface PetQueryService {
    /**
     * Handle get all pets query
     * @param query the {@link GetAllPetsQuery} query
     * @return a list of {@link Pet} entities
     */
    List<Pet> handle(GetAllPetsQuery query);

    /**
     * Handle get pet by id query
     * @param query the {@link GetPetByIdQuery} query
     * @return an {@link Optional} of {@link Pet} entity
     */
    Optional<Pet> handle(GetPetByIdQuery query);

    /**
     * Handle get pets by user id query
     * @param query the {@link GetPetsByUserIdQuery} query
     * @return a list of {@link Pet} entities
     */
    List<Pet> handle(GetPetsByUserIdQuery query);
}

