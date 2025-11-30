package com.dogimax.dogimaxapi.appointments.interfaces.rest.transform;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.veterinary;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.AppointmentResource;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.veterinaryResource;

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
                entity.getNotas(),
                entity.getVeterinaryStatus(),
                null // veterinary will be populated by controller
        );
    }
    
    /**
     * Convert an Appointment entity to an AppointmentResource with veterinary clinic info
     * @param entity The appointment entity
     * @param veterinary The veterinary clinic entity
     * @return The appointment resource
     */
    public static AppointmentResource toResourceFromEntity(Appointment entity, veterinary veterinary) {
        veterinaryResource veterinaryResource = null;
        if (veterinary != null) {
            veterinaryResource = veterinaryResourceFromEntityAssembler.toResourceFromEntity(veterinary);
        }
        return new AppointmentResource(
                entity.getId(),
                entity.getMascotaId(),
                entity.getVeterinaryId(),
                entity.getFechaHora(),
                entity.getMotivo(),
                entity.getEstado(),
                entity.getNotas(),
                entity.getVeterinaryStatus(),
                veterinaryResource
        );
    }
}
