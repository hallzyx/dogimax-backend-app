package com.dogimax.dogimaxapi.notification.domain.model.commands;

import java.time.LocalDateTime;

public record CrearNotificacionCommand(
        String titulo,
        String mensaje,
        String canal, // Usamos String para flexibilidad desde el DTO
        Long destinatarioId,
        LocalDateTime fechaProgramada // Puede ser null si es inmediata
) {
}
