package com.dogimax.dogimaxapi.notification.interfaces.rest;

import com.dogimax.dogimaxapi.notification.domain.model.commands.DeleteNotificationCommand;
import com.dogimax.dogimaxapi.notification.domain.model.commands.MarkAllNotificationsAsReadCommand;
import com.dogimax.dogimaxapi.notification.domain.model.commands.MarkNotificationAsReadCommand;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetAllNotificationsQuery;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetNotificationByIdQuery;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetNotificationsByUserIdQuery;
import com.dogimax.dogimaxapi.notification.domain.services.NotificationCommandService;
import com.dogimax.dogimaxapi.notification.domain.services.NotificationQueryService;
import com.dogimax.dogimaxapi.notification.interfaces.rest.resources.CreateNotificationResource;
import com.dogimax.dogimaxapi.notification.interfaces.rest.resources.NotificationResource;
import com.dogimax.dogimaxapi.notification.interfaces.rest.resources.UpdateNotificationResource;
import com.dogimax.dogimaxapi.notification.interfaces.rest.transform.CreateNotificationCommandFromResourceAssembler;
import com.dogimax.dogimaxapi.notification.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import com.dogimax.dogimaxapi.notification.interfaces.rest.transform.UpdateNotificationCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Notifications controller
 * This controller exposes the endpoints to manage notifications
 */
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Available Notification Endpoints")
public class NotificationsController {

    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;

    public NotificationsController(NotificationCommandService notificationCommandService,
                                   NotificationQueryService notificationQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    /**
     * Get all notifications
     * @return List of all notifications
     */
    @GetMapping
    @Operation(summary = "Get all notifications", description = "Retrieve all notifications from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully")
    })
    public ResponseEntity<List<NotificationResource>> getAllNotifications() {
        var getAllNotificationsQuery = new GetAllNotificationsQuery();
        var notifications = notificationQueryService.handle(getAllNotificationsQuery);
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(notificationResources);
    }

    /**
     * Get notification by id
     * @param notificationId The notification id
     * @return The notification resource
     */
    @GetMapping("/{notificationId}")
    @Operation(summary = "Get notification by id", description = "Retrieve a notification by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<NotificationResource> getNotificationById(@PathVariable Long notificationId) {
        var getNotificationByIdQuery = new GetNotificationByIdQuery(notificationId);
        var notification = notificationQueryService.handle(getNotificationByIdQuery);
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return ResponseEntity.ok(notificationResource);
    }

    /**
     * Get notifications by user id
     * @param userId The user id
     * @return List of notifications for the user
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get notifications by user", description = "Retrieve all notifications for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully")
    })
    public ResponseEntity<List<NotificationResource>> getNotificationsByUserId(@PathVariable Long userId) {
        var query = new GetNotificationsByUserIdQuery(userId);
        var notifications = notificationQueryService.handle(query);
        var notificationResources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(notificationResources);
    }

    /**
     * Create a new notification
     * @param createNotificationResource The notification data
     * @return The created notification
     */
    @PostMapping
    @Operation(summary = "Create notification", description = "Create a new notification in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid notification data")
    })
    public ResponseEntity<NotificationResource> createNotification(
            @RequestBody CreateNotificationResource createNotificationResource) {
        var createNotificationCommand = 
                CreateNotificationCommandFromResourceAssembler.toCommandFromResource(createNotificationResource);
        var notification = notificationCommandService.handle(createNotificationCommand);
        if (notification.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return new ResponseEntity<>(notificationResource, HttpStatus.CREATED);
    }

    /**
     * Update an existing notification
     * @param notificationId The notification id
     * @param updateNotificationResource The updated notification data
     * @return The updated notification
     */
    @PutMapping("/{notificationId}")
    @Operation(summary = "Update notification", description = "Update an existing notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification updated successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "400", description = "Invalid notification data")
    })
    public ResponseEntity<NotificationResource> updateNotification(
            @PathVariable Long notificationId,
            @RequestBody UpdateNotificationResource updateNotificationResource) {
        var updateNotificationCommand = 
                UpdateNotificationCommandFromResourceAssembler.toCommandFromResource(updateNotificationResource, notificationId);
        var notification = notificationCommandService.handle(updateNotificationCommand);
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return ResponseEntity.ok(notificationResource);
    }

    /**
     * Delete a notification
     * @param notificationId The notification id
     * @return No content
     */
    @DeleteMapping("/{notificationId}")
    @Operation(summary = "Delete notification", description = "Delete a notification from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notification deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        var deleteNotificationCommand = new DeleteNotificationCommand(notificationId);
        try {
            notificationCommandService.handle(deleteNotificationCommand);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Mark a notification as read
     * @param notificationId The notification id
     * @return The updated notification
     */
    @PatchMapping("/{notificationId}/mark-as-read")
    @Operation(summary = "Mark notification as read", description = "Mark a specific notification as read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marked as read successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    public ResponseEntity<NotificationResource> markNotificationAsRead(@PathVariable Long notificationId) {
        var markAsReadCommand = new MarkNotificationAsReadCommand(notificationId);
        var notification = notificationCommandService.handle(markAsReadCommand);
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return ResponseEntity.ok(notificationResource);
    }

    /**
     * Mark all notifications as read for a user
     * @param userId The user id
     * @return No content
     */
    @PatchMapping("/user/{userId}/mark-all-as-read")
    @Operation(summary = "Mark all notifications as read", description = "Mark all notifications for a user as read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All notifications marked as read successfully")
    })
    public ResponseEntity<Void> markAllNotificationsAsRead(@PathVariable Long userId) {
        var markAllAsReadCommand = new MarkAllNotificationsAsReadCommand(userId);
        notificationCommandService.handle(markAllAsReadCommand);
        return ResponseEntity.noContent().build();
    }
}
