package com.dogimax.dogimaxapi.notification.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CrearNotificacionResource(
        String titulo,
        String mensaje,
        String canal, // "EMAIL", "PUSH", "SMS"
        Long destinatarioId,
        LocalDateTime fechaProgramada // (Opcional)
) {
}
