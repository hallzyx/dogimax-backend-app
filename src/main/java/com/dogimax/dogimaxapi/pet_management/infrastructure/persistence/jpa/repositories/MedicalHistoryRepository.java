package com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface is responsible for providing the MedicalHistory entity related operations.
 * It extends the JpaRepository interface.
 */
@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    /**
     * This method is responsible for finding all medical histories by pet id.
     * @param petId The pet id.
     * @return The list of medical histories.
     */
    List<MedicalHistory> findByPetId(Long petId);

    /**
     * This method is responsible for finding all medical histories by pet id and record type.
     * @param petId The pet id.
     * @param recordType The record type.
     * @return The list of medical histories.
     */
    List<MedicalHistory> findByPetIdAndRecordType(Long petId, MedicalHistory.RecordType recordType);
}
