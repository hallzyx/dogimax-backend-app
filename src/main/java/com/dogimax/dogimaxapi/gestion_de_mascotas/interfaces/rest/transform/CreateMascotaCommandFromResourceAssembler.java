package com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.transform;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates.Mascota;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands.CreateMascotaCommand;
import com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.resources.CreateMascotaResource;

/**
 * Assembler to convert a CreateMascotaResource to a CreateMascotaCommand
 */
public class CreateMascotaCommandFromResourceAssembler {
    /**
     * Converts a CreateMascotaResource to a CreateMascotaCommand
     * @param resource the CreateMascotaResource
     * @return the CreateMascotaCommand
     */
    public static CreateMascotaCommand toCommandFromResource(CreateMascotaResource resource) {
        Mascota.Sexo sexo = null;
        if (resource.sexo() != null) {
            try {
                sexo = Mascota.Sexo.valueOf(resource.sexo().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Si el valor no es válido, se deja como null
            }
        }
        
        return new CreateMascotaCommand(
                resource.nombre(),
                resource.especie(),
                resource.raza(),
                resource.fechaNacimiento(),
                sexo,
                resource.peso(),
                resource.unidadPeso(),
                resource.descripcion(),
                resource.fotoUrl(),
                resource.userId()
        );
    }
}

