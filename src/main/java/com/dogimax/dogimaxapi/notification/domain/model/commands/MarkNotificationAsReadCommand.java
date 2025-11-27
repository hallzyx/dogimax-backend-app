package com.dogimax.dogimaxapi.notification.domain.model.commands;

/**
 * Command to mark a notification as read
 */
public record MarkNotificationAsReadCommand(Long notificationId) {
}
