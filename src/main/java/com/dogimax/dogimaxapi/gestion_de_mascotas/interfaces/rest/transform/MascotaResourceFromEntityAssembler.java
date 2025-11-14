package com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.transform;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates.Mascota;
import com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.resources.MascotaResource;

/**
 * Assembler to convert a Mascota entity to a MascotaResource
 */
public class MascotaResourceFromEntityAssembler {
    /**
     * Converts a Mascota entity to a MascotaResource
     * @param entity the Mascota entity
     * @return the MascotaResource
     */
    public static MascotaResource toResourceFromEntity(Mascota entity) {
        return new MascotaResource(
                entity.getId(),
                entity.getNombre(),
                entity.getEspecie(),
                entity.getRaza(),
                entity.getFechaNacimiento(),
                entity.getSexo() != null ? entity.getSexo().name() : null,
                entity.getPeso(),
                entity.getUnidadPeso(),
                entity.getDescripcion(),
                entity.getFotoUrl(),
                entity.getUserId(),
                entity.getActiva()
        );
    }
}

