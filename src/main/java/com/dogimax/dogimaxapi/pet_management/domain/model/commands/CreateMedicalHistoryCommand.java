package com.dogimax.dogimaxapi.pet_management.domain.model.commands;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.MedicalHistory;
import java.time.LocalDateTime;

/**
 * Command to create a medical history record
 */
public record CreateMedicalHistoryCommand(
        Long petId,
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
