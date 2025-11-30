package com.dogimax.dogimaxapi.appointments.application.internal.commandservices;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.CreateAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.DeleteAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.UpdateAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.UpdateAppointmentVeterinaryStatusCommand;
import com.dogimax.dogimaxapi.appointments.domain.services.AppointmentCommandService;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.AppointmentRepository;
import com.dogimax.dogimaxapi.notification.domain.model.commands.CreateNotificationCommand;
import com.dogimax.dogimaxapi.notification.domain.services.NotificationCommandService;
import com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Appointment command service implementation
 * This class implements the appointment command service interface
 */
@Service
public class AppointmentCommandServiceImpl implements AppointmentCommandService {

    private final AppointmentRepository appointmentRepository;
    private final NotificationCommandService notificationCommandService;
    private final PetRepository petRepository;

    public AppointmentCommandServiceImpl(
            AppointmentRepository appointmentRepository,
            NotificationCommandService notificationCommandService,
            PetRepository petRepository) {
        this.appointmentRepository = appointmentRepository;
        this.notificationCommandService = notificationCommandService;
        this.petRepository = petRepository;
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

    @Override
    public Optional<Appointment> handle(UpdateAppointmentVeterinaryStatusCommand command) {
        var appointmentId = command.appointmentId();
        
        if (!appointmentRepository.existsById(appointmentId)) {
            return Optional.empty();
        }

        var appointmentToUpdate = appointmentRepository.findById(appointmentId).get();
        var previousStatus = appointmentToUpdate.getVeterinaryStatus();
        appointmentToUpdate.setVeterinaryStatus(command.veterinaryStatus());

        var updatedAppointment = appointmentRepository.save(appointmentToUpdate);

        // Create notification for pet owner if status changed
        if (!command.veterinaryStatus().equals(previousStatus)) {
            var pet = petRepository.findById(updatedAppointment.getMascotaId()).orElse(null);
            if (pet != null) {
                var petOwnerId = pet.getUserId();
                String message = createNotificationMessage(
                        updatedAppointment.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        command.veterinaryStatus(),
                        pet.getName()
                );
                var createNotificationCommand = new CreateNotificationCommand(
                        petOwnerId,
                        message,
                        "appointment_status"
                );
                notificationCommandService.handle(createNotificationCommand);
            }
        }

        return Optional.of(updatedAppointment);
    }

    private String createNotificationMessage(String appointmentDate, String status, String petName) {
        return switch (status) {
            case "ACCEPTED" -> "Tu cita del " + appointmentDate + " para " + petName + " ha sido ACEPTADA por el veterinario.";
            case "REJECTED" -> "Tu cita del " + appointmentDate + " para " + petName + " ha sido RECHAZADA por el veterinario.";
            case "COMPLETED" -> "Tu cita del " + appointmentDate + " para " + petName + " ha sido marcada como COMPLETADA.";
            default -> "El estado de tu cita del " + appointmentDate + " para " + petName + " ha sido actualizado.";
        };
    }
}
