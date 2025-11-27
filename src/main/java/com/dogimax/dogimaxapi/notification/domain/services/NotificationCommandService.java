package com.dogimax.dogimaxapi.notification.domain.services;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notification;
import com.dogimax.dogimaxapi.notification.domain.model.commands.*;

import java.util.Optional;

/**
 * Notification command service interface
 * This interface defines the methods to handle notification commands
 */
public interface NotificationCommandService {
    /**
     * Handle create notification command
     * @param command The create notification command
     * @return The created notification
     */
    Optional<Notification> handle(CreateNotificationCommand command);

    /**
     * Handle update notification command
     * @param command The update notification command
     * @return The updated notification
     */
    Optional<Notification> handle(UpdateNotificationCommand command);

    /**
     * Handle delete notification command
     * @param command The delete notification command
     */
    void handle(DeleteNotificationCommand command);

    /**
     * Handle mark notification as read command
     * @param command The mark notification as read command
     * @return The updated notification
     */
    Optional<Notification> handle(MarkNotificationAsReadCommand command);

    /**
     * Handle mark all notifications as read command
     * @param command The mark all notifications as read command
     * @return List of updated notifications
     */
    void handle(MarkAllNotificationsAsReadCommand command);
}
