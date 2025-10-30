package com.dogimax.dogimaxapi.appointments.interfaces.rest.transform;

import com.dogimax.dogimaxapi.appointments.domain.model.commands.CreateAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.CreateAppointmentResource;

/**
 * Assembler to convert CreateAppointmentResource to CreateAppointmentCommand
 */
public class CreateAppointmentCommandFromResourceAssembler {
    
    /**
     * Convert a CreateAppointmentResource to a CreateAppointmentCommand
     * @param resource The create appointment resource
     * @return The create appointment command
     */
    public static CreateAppointmentCommand toCommandFromResource(CreateAppointmentResource resource) {
        return new CreateAppointmentCommand(
                resource.mascotaId(),
                resource.veterinaryId(),
                resource.fechaHora(),
                resource.motivo(),
                resource.estado(),
                resource.notas()
        );
    }
}
