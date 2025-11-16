package com.dogimax.dogimaxapi.notification.infrastructure.persistence.jpa.repositories;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notificacion;
import com.dogimax.dogimaxapi.notification.domain.model.repositories.NotificacionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionJPARepository extends JpaRepository<Notificacion, Long>, NotificacionRepository {

    // --- Nuevo método para el Query Service ---
    // Spring Data JPA entenderá este método y generará el SQL
    List<Notificacion> findByDestinatarioId(Long destinatarioId);
}
