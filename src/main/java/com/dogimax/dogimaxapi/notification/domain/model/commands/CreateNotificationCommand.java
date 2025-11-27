package com.dogimax.dogimaxapi.notification.domain.model.commands;

/**
 * Command to create a notification
 */
public record CreateNotificationCommand(
        Long userId,
        String message,
        String type
) {
}

