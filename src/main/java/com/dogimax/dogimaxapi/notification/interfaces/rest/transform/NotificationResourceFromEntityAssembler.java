package com.dogimax.dogimaxapi.notification.interfaces.rest.transform;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notification;
import com.dogimax.dogimaxapi.notification.interfaces.rest.resources.NotificationResource;

/**
 * Assembler to convert Notification entity to NotificationResource
 */
public class NotificationResourceFromEntityAssembler {

    /**
     * Convert a Notification entity to a NotificationResource
     * @param entity The notification entity
     * @return The notification resource
     */
    public static NotificationResource toResourceFromEntity(Notification entity) {
        return new NotificationResource(
                entity.getId(),
                entity.getUserId(),
                entity.getMessage(),
                entity.getType(),
                entity.getIsRead(),
                entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null
        );
    }
}
