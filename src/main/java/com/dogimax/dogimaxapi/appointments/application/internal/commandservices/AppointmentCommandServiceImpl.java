package com.dogimax.dogimaxapi.appointments.application.internal.commandservices;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.CreateAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.DeleteAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.UpdateAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.domain.services.AppointmentCommandService;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Appointment command service implementation
 * This class implements the appointment command service interface
 */
@Service
public class AppointmentCommandServiceImpl implements AppointmentCommandService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentCommandServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Optional<Appointment> handle(CreateAppointmentCommand command) {
        var appointment = new Appointment(
                command.mascotaId(),
                command.veterinaryId(),
                command.fechaHora(),
                command.motivo(),
                command.estado(),
                command.notas()
        );
        var createdAppointment = appointmentRepository.save(appointment);
        return Optional.of(createdAppointment);
    }

    @Override
    public Optional<Appointment> handle(UpdateAppointmentCommand command) {
        var appointmentId = command.id();
        
        if (!appointmentRepository.existsById(appointmentId)) {
            return Optional.empty();
        }

        var appointmentToUpdate = appointmentRepository.findById(appointmentId).get();
        appointmentToUpdate.setMascotaId(command.mascotaId());
        appointmentToUpdate.setVeterinaryId(command.veterinaryId());
        appointmentToUpdate.setFechaHora(command.fechaHora());
        appointmentToUpdate.setMotivo(command.motivo());
        appointmentToUpdate.setEstado(command.estado());
        appointmentToUpdate.setNotas(command.notas());

        var updatedAppointment = appointmentRepository.save(appointmentToUpdate);
        return Optional.of(updatedAppointment);
    }

    @Override
    public void handle(DeleteAppointmentCommand command) {
        if (!appointmentRepository.existsById(command.appointmentId())) {
            throw new IllegalArgumentException("Appointment with id " + command.appointmentId() + " does not exist");
        }
        appointmentRepository.deleteById(command.appointmentId());
    }
}
