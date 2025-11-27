package com.dogimax.dogimaxapi.notification.interfaces.rest.resources;

/**
 * Notification resource
 * This resource represents a notification in the REST API
 */
public record NotificationResource(
        Long id,
        Long userId,
        String message,
        String type,
        Boolean isRead,
        String createdAt
) {
}

