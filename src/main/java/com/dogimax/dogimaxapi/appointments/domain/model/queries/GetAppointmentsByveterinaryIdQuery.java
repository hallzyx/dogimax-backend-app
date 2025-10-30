package com.dogimax.dogimaxapi.appointments.domain.model.queries;

/**
 * Query to get appointments by veterinary clinic ID
 */
public record GetAppointmentsByveterinaryIdQuery(Long veterinaryId) {
}
