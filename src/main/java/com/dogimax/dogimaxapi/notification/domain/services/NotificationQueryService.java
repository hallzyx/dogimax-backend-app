package com.dogimax.dogimaxapi.notification.domain.services;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notification;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetAllNotificationsQuery;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetNotificationByIdQuery;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetNotificationsByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Notification query service interface
 * This interface defines the methods to handle notification queries
 */
public interface NotificationQueryService {
    /**
     * Handle get all notifications query
     * @param query The get all notifications query
     * @return List of all notifications
     */
    List<Notification> handle(GetAllNotificationsQuery query);

    /**
     * Handle get notification by id query
     * @param query The get notification by id query
     * @return The notification if found
     */
    Optional<Notification> handle(GetNotificationByIdQuery query);

    /**
     * Handle get notifications by user id query
     * @param query The get notifications by user id query
     * @return List of notifications for the user
     */
    List<Notification> handle(GetNotificationsByUserIdQuery query);
}
