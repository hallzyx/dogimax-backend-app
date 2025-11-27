package com.dogimax.dogimaxapi.notification.application.internal.commandservices;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notification;
import com.dogimax.dogimaxapi.notification.domain.model.commands.*;
import com.dogimax.dogimaxapi.notification.domain.services.NotificationCommandService;
import com.dogimax.dogimaxapi.notification.infrastructure.persistence.jpa.repositories.NotificationJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Notification command service implementation
 * This class implements the notification command service interface
 */
@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationJPARepository notificationJPARepository;

    public NotificationCommandServiceImpl(NotificationJPARepository notificationJPARepository) {
        this.notificationJPARepository = notificationJPARepository;
    }

    @Override
    public Optional<Notification> handle(CreateNotificationCommand command) {
        var notification = new Notification(
                command.userId(),
                command.message(),
                command.type()
        );
        var createdNotification = notificationJPARepository.save(notification);
        return Optional.of(createdNotification);
    }

    @Override
    public Optional<Notification> handle(UpdateNotificationCommand command) {
        var notificationId = command.notificationId();
        
        if (!notificationJPARepository.existsById(notificationId)) {
            return Optional.empty();
        }

        var notificationToUpdate = notificationJPARepository.findById(notificationId).get();
        notificationToUpdate.setUserId(command.userId());
        notificationToUpdate.setMessage(command.message());
        notificationToUpdate.setType(command.type());
        notificationToUpdate.setIsRead(command.isRead());

        var updatedNotification = notificationJPARepository.save(notificationToUpdate);
        return Optional.of(updatedNotification);
    }

    @Override
    public void handle(DeleteNotificationCommand command) {
        if (!notificationJPARepository.existsById(command.notificationId())) {
            throw new IllegalArgumentException("Notification with id " + command.notificationId() + " does not exist");
        }
        notificationJPARepository.deleteById(command.notificationId());
    }

    @Override
    public Optional<Notification> handle(MarkNotificationAsReadCommand command) {
        var notificationId = command.notificationId();
        
        if (!notificationJPARepository.existsById(notificationId)) {
            return Optional.empty();
        }

        var notification = notificationJPARepository.findById(notificationId).get();
        notification.markAsRead();

        var updatedNotification = notificationJPARepository.save(notification);
        return Optional.of(updatedNotification);
    }

    @Override
    public void handle(MarkAllNotificationsAsReadCommand command) {
        var notifications = notificationJPARepository.findByUserId(command.userId());
        
        for (Notification notification : notifications) {
            if (!notification.getIsRead()) {
                notification.markAsRead();
                notificationJPARepository.save(notification);
            }
        }
    }
}

