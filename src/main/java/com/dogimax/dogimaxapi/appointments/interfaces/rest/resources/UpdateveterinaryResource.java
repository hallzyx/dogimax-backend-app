package com.dogimax.dogimaxapi.appointments.interfaces.rest.resources;

import java.util.List;

/**
 * Update veterinary resource
 */
public record UpdateveterinaryResource(
        String nombre,
        String direccion,
        String telefono,
        List<String> servicios,
        String horario
) {
}
