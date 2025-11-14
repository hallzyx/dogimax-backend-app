package com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.transform;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates.Mascota;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands.UpdateMascotaCommand;
import com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.resources.UpdateMascotaResource;

/**
 * Assembler to convert an UpdateMascotaResource to an UpdateMascotaCommand
 */
public class UpdateMascotaCommandFromResourceAssembler {
    /**
     * Converts an UpdateMascotaResource to an UpdateMascotaCommand
     * @param id the mascota id
     * @param resource the UpdateMascotaResource
     * @return the UpdateMascotaCommand
     */
    public static UpdateMascotaCommand toCommandFromResource(Long id, UpdateMascotaResource resource) {
        Mascota.Sexo sexo = null;
        if (resource.sexo() != null) {
            try {
                sexo = Mascota.Sexo.valueOf(resource.sexo().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Si el valor no es válido, se deja como null
            }
        }
        
        return new UpdateMascotaCommand(
                id,
                resource.nombre(),
                resource.especie(),
                resource.raza(),
                resource.fechaNacimiento(),
                sexo,
                resource.peso(),
                resource.unidadPeso(),
                resource.descripcion(),
                resource.fotoUrl()
        );
    }
}

