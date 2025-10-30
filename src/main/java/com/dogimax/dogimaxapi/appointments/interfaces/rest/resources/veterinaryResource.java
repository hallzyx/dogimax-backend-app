package com.dogimax.dogimaxapi.appointments.interfaces.rest.resources;

import java.util.List;

/**
 * veterinary resource
 */
public record veterinaryResource(
        Long id,
        String nombre,
        String direccion,
        String telefono,
        List<String> servicios,
        String horario
) {
}
