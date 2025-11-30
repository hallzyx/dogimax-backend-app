package com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.VeterinaryStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for VeterinaryStaff aggregate
 * Manages the many-to-many relationship between veterinary users and clinics
 */
@Repository
public interface VeterinaryStaffRepository extends JpaRepository<VeterinaryStaff, Long> {
    
    /**
     * Find all active veterinary staff by user ID
     * @param userId The user ID
     * @return List of active veterinary staff entries
     */
    List<VeterinaryStaff> findByUserIdAndIsActiveTrue(Long userId);
    
    /**
     * Find all active veterinary staff by veterinary clinic ID
     * @param veterinaryId The veterinary clinic ID
     * @return List of active veterinary staff entries
     */
    List<VeterinaryStaff> findByVeterinaryIdAndIsActiveTrue(Long veterinaryId);
    
    /**
     * Find veterinary staff by user ID and veterinary ID
     * @param userId The user ID
     * @param veterinaryId The veterinary ID
     * @return Optional of VeterinaryStaff
     */
    Optional<VeterinaryStaff> findByUserIdAndVeterinaryId(Long userId, Long veterinaryId);
    
    /**
     * Check if a veterinary staff entry exists and is active
     * @param userId The user ID
     * @param veterinaryId The veterinary ID
     * @return true if exists and is active
     */
    boolean existsByUserIdAndVeterinaryIdAndIsActiveTrue(Long userId, Long veterinaryId);
}
