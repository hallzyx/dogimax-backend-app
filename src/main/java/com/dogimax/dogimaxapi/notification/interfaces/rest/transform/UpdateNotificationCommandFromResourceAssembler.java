package com.dogimax.dogimaxapi.notification.interfaces.rest.transform;

import com.dogimax.dogimaxapi.notification.domain.model.commands.UpdateNotificationCommand;
import com.dogimax.dogimaxapi.notification.interfaces.rest.resources.UpdateNotificationResource;

/**
 * Assembler to convert UpdateNotificationResource to UpdateNotificationCommand
 */
public class UpdateNotificationCommandFromResourceAssembler {
    
    /**
     * Convert an UpdateNotificationResource to an UpdateNotificationCommand
     * @param resource The update notification resource
     * @param notificationId The notification id
     * @return The update notification command
     */
    public static UpdateNotificationCommand toCommandFromResource(UpdateNotificationResource resource, Long notificationId) {
        return new UpdateNotificationCommand(
                notificationId,
                resource.userId(),
                resource.message(),
                resource.type(),
                resource.isRead()
        );
    }
}
