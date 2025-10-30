package com.dogimax.dogimaxapi.appointments.interfaces.rest.resources;

import java.util.List;

/**
 * Create veterinary resource
 */
public record CreateveterinaryResource(
        String nombre,
        String direccion,
        String telefono,
        List<String> servicios,
        String horario
) {
}
