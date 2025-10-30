package com.dogimax.dogimaxapi.appointments.domain.model.commands;

import java.time.LocalDateTime;

/**
 * Command to update an existing appointment
 */
public record UpdateAppointmentCommand(
        Long id,
        Long mascotaId,
        Long veterinaryId,
        LocalDateTime fechaHora,
        String motivo,
        String estado,
        String notas
) {
}
