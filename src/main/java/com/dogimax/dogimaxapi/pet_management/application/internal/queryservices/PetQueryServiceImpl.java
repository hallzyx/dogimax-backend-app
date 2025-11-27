package com.dogimax.dogimaxapi.pet_management.application.internal.queryservices;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;
import com.dogimax.dogimaxapi.pet_management.domain.model.queries.GetAllPetsQuery;
import com.dogimax.dogimaxapi.pet_management.domain.model.queries.GetPetByIdQuery;
import com.dogimax.dogimaxapi.pet_management.domain.model.queries.GetPetsByUserIdQuery;
import com.dogimax.dogimaxapi.pet_management.domain.services.PetQueryService;
import com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Pet query service implementation
 * <p>
 *     This class implements the {@link PetQueryService} interface and provides the implementation for the
 *     pet queries.
 * </p>
 */
@Service
public class PetQueryServiceImpl implements PetQueryService {

    private final PetRepository petRepository;

    public PetQueryServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public List<Pet> handle(GetAllPetsQuery query) {
        return petRepository.findAll();
    }

    @Override
    public Optional<Pet> handle(GetPetByIdQuery query) {
        return petRepository.findById(query.id());
    }

    @Override
    public List<Pet> handle(GetPetsByUserIdQuery query) {
        return petRepository.findByUserId(query.userId());
    }
}

