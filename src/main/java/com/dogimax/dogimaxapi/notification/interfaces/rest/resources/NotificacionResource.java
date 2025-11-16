package com.dogimax.dogimaxapi.notification.interfaces.rest.resources;

import java.time.LocalDateTime;

public record NotificacionResource(
        Long id,
        String titulo,
        String mensaje,
        String canal,
        String estado,
        LocalDateTime fechaProgramada,
        LocalDateTime fechaEnviada
) {
}
