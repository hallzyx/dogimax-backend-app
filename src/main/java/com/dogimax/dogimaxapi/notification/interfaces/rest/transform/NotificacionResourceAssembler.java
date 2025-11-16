package com.dogimax.dogimaxapi.notification.interfaces.rest.transform;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notificacion;
import com.dogimax.dogimaxapi.notification.interfaces.rest.resources.NotificacionResource;

public class NotificacionResourceAssembler {

    public static NotificacionResource toResourceFromEntity(Notificacion entity) {
        return new NotificacionResource(
                entity.getId(),
                entity.getTitulo(),
                entity.getMensaje(),
                entity.getCanal().toString(), // Convertir Enum a String
                entity.getEstado().toString(), // Convertir Enum a String
                entity.getFechaProgramada(),
                entity.getFechaEnviada()
        );
    }
}