package com.dogimax.dogimaxapi.notification.domain.model.commands;

/**
 * Command to update a notification
 */
public record UpdateNotificationCommand(
        Long notificationId,
        Long userId,
        String message,
        String type,
        Boolean isRead
) {
}
