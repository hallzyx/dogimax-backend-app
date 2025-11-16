package com.dogimax.dogimaxapi.notification.aplication;

import com.dogimax.dogimaxapi.notification.domain.model.commands.CrearNotificacionCommand;
import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notificacion;
import com.dogimax.dogimaxapi.notification.domain.model.commands.MarcarNotificacionEnviadaCommand;
import java.util.Optional;

public interface NotificacionCommandService {
    Optional<Notificacion> handle(CrearNotificacionCommand command);

    Optional<Notificacion> handle(MarcarNotificacionEnviadaCommand command);
}
