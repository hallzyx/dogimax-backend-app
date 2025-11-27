package com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface is responsible for providing the Pet entity related operations.
 * It extends the JpaRepository interface.
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    /**
     * This method is responsible for finding all pets by user id.
     * @param userId The user id.
     * @return The list of pets.
     */
    List<Pet> findByUserId(Long userId);

    /**
     * This method is responsible for finding all active pets by user id.
     * @param userId The user id.
     * @param isActive The active status.
     * @return The list of pets.
     */
    List<Pet> findByUserIdAndIsActive(Long userId, Boolean isActive);

    /**
     * This method is responsible for finding a pet by id and user id.
     * @param id The pet id.
     * @param userId The user id.
     * @return The pet object.
     */
    Optional<Pet> findByIdAndUserId(Long id, Long userId);
}

