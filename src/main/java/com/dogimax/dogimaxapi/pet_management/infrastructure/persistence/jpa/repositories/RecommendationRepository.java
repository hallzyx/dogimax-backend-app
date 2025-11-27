package com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface is responsible for providing the Recommendation entity related operations.
 * It extends the JpaRepository interface.
 */
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    /**
     * This method is responsible for finding all recommendations by pet id.
     * @param petId The pet id.
     * @return The list of recommendations.
     */
    List<Recommendation> findByPetId(Long petId);

    /**
     * This method is responsible for finding all recommendations by pet id and completion status.
     * @param petId The pet id.
     * @param isCompleted The completion status.
     * @return The list of recommendations.
     */
    List<Recommendation> findByPetIdAndIsCompleted(Long petId, Boolean isCompleted);

    /**
     * This method is responsible for finding all recommendations by pet id and priority.
     * @param petId The pet id.
     * @param priority The priority level.
     * @return The list of recommendations.
     */
    List<Recommendation> findByPetIdAndPriority(Long petId, Recommendation.Priority priority);
}
