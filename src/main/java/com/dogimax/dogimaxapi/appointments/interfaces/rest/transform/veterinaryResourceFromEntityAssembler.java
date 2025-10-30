package com.dogimax.dogimaxapi.appointments.interfaces.rest.transform;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.veterinary;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.veterinaryResource;

/**
 * Assembler to convert veterinary entity to veterinaryResource
 */
public class veterinaryResourceFromEntityAssembler {
    
    public static veterinaryResource toResourceFromEntity(veterinary entity) {
        return new veterinaryResource(
                entity.getId(),
                entity.getNombre(),
                entity.getDireccion(),
                entity.getTelefono(),
                entity.getServicios(),
                entity.getHorario()
        );
    }
}
