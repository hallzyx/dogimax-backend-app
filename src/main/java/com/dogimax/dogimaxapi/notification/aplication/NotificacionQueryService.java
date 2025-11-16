package com.dogimax.dogimaxapi.notification.aplication;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notificacion;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetNotificacionesByDestinatarioIdQuery;
import java.util.List;

public interface NotificacionQueryService {

    // El handler recibe el Query y retorna una lista de Agregados
    List<Notificacion> handle(GetNotificacionesByDestinatarioIdQuery query);
}