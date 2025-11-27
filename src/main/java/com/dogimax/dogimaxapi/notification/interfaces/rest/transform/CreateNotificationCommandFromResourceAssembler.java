package com.dogimax.dogimaxapi.notification.interfaces.rest.transform;

import com.dogimax.dogimaxapi.notification.domain.model.commands.CreateNotificationCommand;
import com.dogimax.dogimaxapi.notification.interfaces.rest.resources.CreateNotificationResource;

/**
 * Assembler to convert CreateNotificationResource to CreateNotificationCommand
 */
public class CreateNotificationCommandFromResourceAssembler {
    
    /**
     * Convert a CreateNotificationResource to a CreateNotificationCommand
     * @param resource The create notification resource
     * @return The create notification command
     */
    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
        return new CreateNotificationCommand(
                resource.userId(),
                resource.message(),
                resource.type()
        );
    }
}
