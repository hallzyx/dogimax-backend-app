package com.dogimax.dogimaxapi.notification.application.internal.queryservices;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notification;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetAllNotificationsQuery;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetNotificationByIdQuery;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetNotificationsByUserIdQuery;
import com.dogimax.dogimaxapi.notification.domain.services.NotificationQueryService;
import com.dogimax.dogimaxapi.notification.infrastructure.persistence.jpa.repositories.NotificationJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Notification query service implementation
 * This class implements the notification query service interface
 */
@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationJPARepository notificationJPARepository;

    public NotificationQueryServiceImpl(NotificationJPARepository notificationJPARepository) {
        this.notificationJPARepository = notificationJPARepository;
    }

    @Override
    public List<Notification> handle(GetAllNotificationsQuery query) {
        return notificationJPARepository.findAll();
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return notificationJPARepository.findById(query.notificationId());
    }

    @Override
    public List<Notification> handle(GetNotificationsByUserIdQuery query) {
        return notificationJPARepository.findByUserId(query.userId());
    }
}

