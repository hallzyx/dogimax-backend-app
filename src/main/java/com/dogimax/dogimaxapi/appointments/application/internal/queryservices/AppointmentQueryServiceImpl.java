package com.dogimax.dogimaxapi.appointments.application.internal.queryservices;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAllAppointmentsQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentByIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentsByMascotaIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentsByveterinaryIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.services.AppointmentQueryService;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Appointment query service implementation
 * This class implements the appointment query service interface
 */
@Service
public class AppointmentQueryServiceImpl implements AppointmentQueryService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentQueryServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> handle(GetAllAppointmentsQuery query) {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> handle(GetAppointmentByIdQuery query) {
        return appointmentRepository.findById(query.appointmentId());
    }

    @Override
    public List<Appointment> handle(GetAppointmentsByMascotaIdQuery query) {
        return appointmentRepository.findByMascotaId(query.mascotaId());
    }

    @Override
    public List<Appointment> handle(GetAppointmentsByveterinaryIdQuery query) {
        return appointmentRepository.findByVeterinaryId(query.veterinaryId());
    }
}
