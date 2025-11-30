package com.dogimax.dogimaxapi.appointments.domain.model.commands;

import java.time.LocalDateTime;

/**
 * Command to create a new appointment
 */
public record CreateAppointmentCommand(
        Long mascotaId,
        Long veterinaryId, // ID del usuario veterinario
        LocalDateTime fechaHora,
        String motivo,
        String estado,
        String notas
) {
}
