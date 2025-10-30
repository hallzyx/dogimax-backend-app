package com.dogimax.dogimaxapi.appointments.interfaces.rest.transform;

import com.dogimax.dogimaxapi.appointments.domain.model.commands.UpdateAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.UpdateAppointmentResource;

/**
 * Assembler to convert UpdateAppointmentResource to UpdateAppointmentCommand
 */
public class UpdateAppointmentCommandFromResourceAssembler {
    
    /**
     * Convert an UpdateAppointmentResource to an UpdateAppointmentCommand
     * @param resource The update appointment resource
     * @param appointmentId The appointment id
     * @return The update appointment command
     */
    public static UpdateAppointmentCommand toCommandFromResource(UpdateAppointmentResource resource, Long appointmentId) {
        return new UpdateAppointmentCommand(
                appointmentId,
                resource.mascotaId(),
                resource.veterinaryId(),
                resource.fechaHora(),
                resource.motivo(),
                resource.estado(),
                resource.notas()
        );
    }
}
