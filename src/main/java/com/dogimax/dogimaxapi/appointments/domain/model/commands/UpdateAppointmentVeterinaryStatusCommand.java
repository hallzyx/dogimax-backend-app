package com.dogimax.dogimaxapi.appointments.domain.model.commands;

/**
 * Command to update the veterinary status of an appointment
 * Used when a veterinarian accepts or rejects an appointment request
 */
public record UpdateAppointmentVeterinaryStatusCommand(
        Long appointmentId,
        String veterinaryStatus // PENDING, ACCEPTED, REJECTED, COMPLETED
) {
}
