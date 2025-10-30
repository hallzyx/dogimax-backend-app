package com.dogimax.dogimaxapi.appointments.interfaces.rest.transform;

import com.dogimax.dogimaxapi.appointments.domain.model.commands.UpdateveterinaryCommand;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.UpdateveterinaryResource;

/**
 * Assembler to convert UpdateveterinaryResource to UpdateveterinaryCommand
 */
public class UpdateveterinaryCommandFromResourceAssembler {
    
    public static UpdateveterinaryCommand toCommandFromResource(UpdateveterinaryResource resource, Long veterinaryId) {
        return new UpdateveterinaryCommand(
                veterinaryId,
                resource.nombre(),
                resource.direccion(),
                resource.telefono(),
                resource.servicios(),
                resource.horario()
        );
    }
}
