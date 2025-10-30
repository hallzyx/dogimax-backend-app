package com.dogimax.dogimaxapi.appointments.domain.services;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.veterinary;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.CreateveterinaryCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.DeleteveterinaryCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.commands.UpdateveterinaryCommand;

import java.util.Optional;

/**
 * veterinary command service interface
 */
public interface VeterinaryCommandService {
    Optional<veterinary> handle(CreateveterinaryCommand command);
    Optional<veterinary> handle(UpdateveterinaryCommand command);
    void handle(DeleteveterinaryCommand command);
}
