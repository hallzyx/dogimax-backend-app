package com.dogimax.dogimaxapi.appointments.domain.model.commands;

import java.util.List;

/**
 * Command to update an existing veterinary
 */
public record UpdateveterinaryCommand(
        Long id,
        String nombre,
        String direccion,
        String telefono,
        List<String> servicios,
        String horario
) {
}
