package com.dogimax.dogimaxapi.appointments.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Create appointment resource
 * This resource represents the data needed to create an appointment
 */
public record CreateAppointmentResource(
        Long mascotaId,
        Long veterinaryId,
        LocalDateTime fechaHora,
        String motivo,
        String estado,
        String notas
) {
}
