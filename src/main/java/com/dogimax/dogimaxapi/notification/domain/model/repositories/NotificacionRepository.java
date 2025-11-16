package com.dogimax.dogimaxapi.notification.domain.model.repositories;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notificacion;
import java.util.Optional;

public interface NotificacionRepository { Notificacion save(Notificacion notificacion);

    Optional<Notificacion> findById(Long id);

    // Podrías necesitar esto para el "RecordatoriosServicio"
    // List<Notificacion> findByEstadoAndFechaProgramadaBefore(EstadoEnvio estado, LocalDateTime ahora);
}
