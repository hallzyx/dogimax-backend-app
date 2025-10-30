package com.dogimax.dogimaxapi.appointments.domain.services;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAllAppointmentsQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentByIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentsByMascotaIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentsByveterinaryIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Appointment query service interface
 * This interface defines the methods to handle appointment queries
 */
public interface AppointmentQueryService {
    /**
     * Handle get all appointments query
     * @param query The get all appointments query
     * @return List of all appointments
     */
    List<Appointment> handle(GetAllAppointmentsQuery query);

    /**
     * Handle get appointment by id query
     * @param query The get appointment by id query
     * @return The appointment if found
     */
    Optional<Appointment> handle(GetAppointmentByIdQuery query);

    /**
     * Handle get appointments by mascota id query
     * @param query The get appointments by mascota id query
     * @return List of appointments for the pet
     */
    List<Appointment> handle(GetAppointmentsByMascotaIdQuery query);

    /**
     * Handle get appointments by veterinary id query
     * @param query The get appointments by veterinary id query
     * @return List of appointments for the veterinary clinic
     */
    List<Appointment> handle(GetAppointmentsByveterinaryIdQuery query);
}
