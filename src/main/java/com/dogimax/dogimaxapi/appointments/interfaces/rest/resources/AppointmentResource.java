package com.dogimax.dogimaxapi.appointments.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Appointment resource
 * This resource represents an appointment in the REST API
 */
public record AppointmentResource(
        Long id,
        Long mascotaId,
        Long veterinaryId,
        LocalDateTime fechaHora,
        String motivo,
        String estado,
        String notas
) {
}
