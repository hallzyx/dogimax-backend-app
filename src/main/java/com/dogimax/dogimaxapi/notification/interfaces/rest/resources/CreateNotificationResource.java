package com.dogimax.dogimaxapi.notification.interfaces.rest.resources;

/**
 * Create notification resource
 * This resource represents the data needed to create a notification
 */
public record CreateNotificationResource(
        Long userId,
        String message,
        String type
) {
}

