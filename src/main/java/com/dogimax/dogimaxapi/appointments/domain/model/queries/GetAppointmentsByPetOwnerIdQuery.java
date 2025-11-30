package com.dogimax.dogimaxapi.appointments.domain.model.queries;

/**
 * Query to get all appointments for a specific pet owner (user)
 * This query will fetch all appointments for pets owned by the specified user
 */
public record GetAppointmentsByPetOwnerIdQuery(Long petOwnerId) {
}
