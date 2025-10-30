package com.dogimax.dogimaxapi.appointments.application.internal.commandservices;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.veterinary;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.CreateveterinaryCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.DeleteveterinaryCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.UpdateveterinaryCommand;
import com.dogimax.dogimaxapi.appointments.domain.services.VeterinaryCommandService;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.veterinaryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * veterinary command service implementation
 */
@Service
public class veterinaryCommandServiceImpl implements VeterinaryCommandService {

    private final veterinaryRepository veterinaryRepository;

    public veterinaryCommandServiceImpl(veterinaryRepository veterinaryRepository) {
        this.veterinaryRepository = veterinaryRepository;
    }

    @Override
    public Optional<veterinary> handle(CreateveterinaryCommand command) {
        var veterinary = new veterinary(
                command.nombre(),
                command.direccion(),
                command.telefono(),
                command.servicios(),
                command.horario()
        );
        var createdveterinary = veterinaryRepository.save(veterinary);
        return Optional.of(createdveterinary);
    }

    @Override
    public Optional<veterinary> handle(UpdateveterinaryCommand command) {
        var veterinaryId = command.id();
        
        if (!veterinaryRepository.existsById(veterinaryId)) {
            return Optional.empty();
        }

        var veterinaryToUpdate = veterinaryRepository.findById(veterinaryId).get();
        veterinaryToUpdate.setNombre(command.nombre());
        veterinaryToUpdate.setDireccion(command.direccion());
        veterinaryToUpdate.setTelefono(command.telefono());
        veterinaryToUpdate.setServicios(command.servicios());
        veterinaryToUpdate.setHorario(command.horario());

        var updatedveterinary = veterinaryRepository.save(veterinaryToUpdate);
        return Optional.of(updatedveterinary);
    }

    @Override
    public void handle(DeleteveterinaryCommand command) {
        if (!veterinaryRepository.existsById(command.veterinaryId())) {
            throw new IllegalArgumentException("veterinary with id " + command.veterinaryId() + " does not exist");
        }
        veterinaryRepository.deleteById(command.veterinaryId());
    }
}
