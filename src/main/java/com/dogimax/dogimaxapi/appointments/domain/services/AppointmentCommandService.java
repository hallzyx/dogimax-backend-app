package com.dogimax.dogimaxapi.appointments.domain.services;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.CreateAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.DeleteAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.UpdateAppointmentCommand;

import java.util.Optional;

/**
 * Appointment command service interface
 * This interface defines the methods to handle appointment commands
 */
public interface AppointmentCommandService {
    /**
     * Handle create appointment command
     * @param command The create appointment command
     * @return The created appointment
     */
    Optional<Appointment> handle(CreateAppointmentCommand command);

    /**
     * Handle update appointment command
     * @param command The update appointment command
     * @return The updated appointment
     */
    Optional<Appointment> handle(UpdateAppointmentCommand command);

    /**
     * Handle delete appointment command
     * @param command The delete appointment command
     */
    void handle(DeleteAppointmentCommand command);
}
