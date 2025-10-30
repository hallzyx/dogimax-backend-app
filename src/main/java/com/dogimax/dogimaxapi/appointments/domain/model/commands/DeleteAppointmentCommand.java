package com.dogimax.dogimaxapi.appointments.domain.model.commands;

/**
 * Command to delete an appointment
 */
public record DeleteAppointmentCommand(Long appointmentId) {
}
