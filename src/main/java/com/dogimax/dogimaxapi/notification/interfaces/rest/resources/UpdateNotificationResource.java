package com.dogimax.dogimaxapi.notification.interfaces.rest.resources;

/**
 * Update notification resource
 * This resource represents the data needed to update a notification
 */
public record UpdateNotificationResource(
        Long userId,
        String message,
        String type,
        Boolean isRead
) {
}
