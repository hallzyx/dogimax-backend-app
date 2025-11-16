package com.dogimax.dogimaxapi.notification.aplication.internal.queryservices;

import com.dogimax.dogimaxapi.notification.aplication.NotificacionQueryService;
import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notificacion;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetNotificacionesByDestinatarioIdQuery;
import com.dogimax.dogimaxapi.notification.infrastructure.persistence.jpa.repositories.NotificacionJPARepository; // Usamos la implementación de Infra
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacionQueryServiceImpl implements NotificacionQueryService {

    private final NotificacionJPARepository notificacionJPARepository;

    public NotificacionQueryServiceImpl(NotificacionJPARepository notificacionJPARepository) {
        this.notificacionJPARepository = notificacionJPARepository;
    }

    @Override
    public List<Notificacion> handle(GetNotificacionesByDestinatarioIdQuery query) {
        // Simplemente llamamos al método del repositorio de JPA
        return notificacionJPARepository.findByDestinatarioId(query.destinatarioId());
    }
}
