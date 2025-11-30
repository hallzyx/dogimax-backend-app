package com.dogimax.dogimaxapi.appointments.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Update appointment resource
 * This resource represents the data needed to update an appointment
 */
public record UpdateAppointmentResource(
        Long mascotaId,
        Long veterinaryId, // ID del usuario veterinario
        LocalDateTime fechaHora,
        String motivo,
        String estado,
        String notas
) {
}
