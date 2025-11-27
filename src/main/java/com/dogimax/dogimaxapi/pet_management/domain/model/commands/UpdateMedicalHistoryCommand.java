package com.dogimax.dogimaxapi.pet_management.domain.model.commands;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.MedicalHistory;
import java.time.LocalDateTime;

/**
 * Command to update a medical history record
 */
public record UpdateMedicalHistoryCommand(
        Long id,
        LocalDateTime registrationDate,
        MedicalHistory.RecordType recordType,
        String description,
        String veterinarian,
        String observations,
        String files,
        Double cost,
        LocalDateTime nextAppointment
) {
}
