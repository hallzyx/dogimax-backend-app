package com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Medical History resource
 * @param id the medical history id
 * @param petId the pet id
 * @param registrationDate the registration date
 * @param recordType the type of medical record
 * @param description the description
 * @param veterinarian the veterinarian name
 * @param observations additional observations
 * @param files files related to the medical history
 * @param cost the cost of the service
 * @param nextAppointment the next appointment date
 */
public record MedicalHistoryResource(
        Long id,
        Long petId,
        LocalDateTime registrationDate,
        String recordType,
        String description,
        String veterinarian,
        String observations,
        String files,
        Double cost,
        LocalDateTime nextAppointment
) {
}
