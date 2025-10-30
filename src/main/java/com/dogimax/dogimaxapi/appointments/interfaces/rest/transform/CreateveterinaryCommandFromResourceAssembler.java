package com.dogimax.dogimaxapi.appointments.interfaces.rest.transform;

import com.dogimax.dogimaxapi.appointments.domain.model.commands.CreateveterinaryCommand;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.CreateveterinaryResource;

/**
 * Assembler to convert CreateveterinaryResource to CreateveterinaryCommand
 */
public class CreateveterinaryCommandFromResourceAssembler {
    
    public static CreateveterinaryCommand toCommandFromResource(CreateveterinaryResource resource) {
        return new CreateveterinaryCommand(
                resource.nombre(),
                resource.direccion(),
                resource.telefono(),
                resource.servicios(),
                resource.horario()
        );
    }
}
