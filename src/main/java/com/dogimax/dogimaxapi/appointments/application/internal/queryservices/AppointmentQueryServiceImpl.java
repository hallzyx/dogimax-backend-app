package com.dogimax.dogimaxapi.appointments.application.internal.queryservices;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAllAppointmentsQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentByIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentsByMascotaIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentsByveterinaryIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAppointmentsByPetOwnerIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.services.AppointmentQueryService;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.AppointmentRepository;
import com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Appointment query service implementation
 * This class implements the appointment query service interface
 */
@Service
public class AppointmentQueryServiceImpl implements AppointmentQueryService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;

    public AppointmentQueryServiceImpl(AppointmentRepository appointmentRepository,
                                      PetRepository petRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
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

    @Override
    public List<Appointment> handle(GetAppointmentsByPetOwnerIdQuery query) {
        // Get all pets owned by the user
        var pets = petRepository.findByUserId(query.petOwnerId());
        
        // Get all pet IDs
        var petIds = pets.stream()
                .map(pet -> pet.getId())
                .collect(Collectors.toList());
        
        // Get all appointments for those pets
        return petIds.stream()
                .flatMap(petId -> appointmentRepository.findByMascotaId(petId).stream())
                .collect(Collectors.toList());
    }
}
