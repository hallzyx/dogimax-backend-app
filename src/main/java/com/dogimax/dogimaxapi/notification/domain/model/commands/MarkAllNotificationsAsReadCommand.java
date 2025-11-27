package com.dogimax.dogimaxapi.notification.domain.model.commands;

/**
 * Command to mark all notifications as read for a user
 */
public record MarkAllNotificationsAsReadCommand(Long userId) {
}
