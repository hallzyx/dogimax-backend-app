package com.dogimax.dogimaxapi.appointments.domain.model.commands;

import java.util.List;

/**
 * Command to create a new veterinary
 */
public record CreateveterinaryCommand(
        String nombre,
        String direccion,
        String telefono,
        List<String> servicios,
        String horario
) {
}
