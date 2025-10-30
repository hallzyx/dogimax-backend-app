package com.dogimax.dogimaxapi.appointments.interfaces.rest.transform;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.AppointmentResource;

/**
 * Assembler to convert Appointment entity to AppointmentResource
 */
public class AppointmentResourceFromEntityAssembler {
    
    /**
     * Convert an Appointment entity to an AppointmentResource
     * @param entity The appointment entity
     * @return The appointment resource
     */
    public static AppointmentResource toResourceFromEntity(Appointment entity) {
        return new AppointmentResource(
                entity.getId(),
                entity.getMascotaId(),
                entity.getVeterinaryId(),
                entity.getFechaHora(),
                entity.getMotivo(),
                entity.getEstado(),
                entity.getNotas()
        );
    }
}
