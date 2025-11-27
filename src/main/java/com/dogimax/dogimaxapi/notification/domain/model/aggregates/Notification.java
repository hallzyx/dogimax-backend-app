package com.dogimax.dogimaxapi.notification.domain.model.aggregates;

import com.dogimax.dogimaxapi.notification.domain.model.enums.Canal;
import com.dogimax.dogimaxapi.notification.domain.model.enums.EstadoEnvio;
import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Notification aggregate root.
 * Represents a notification in the system.
 */
@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification extends AuditableAbstractAggregateRoot<Notification> {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    /**
     * Default constructor
     */
    public Notification() {
        super();
        this.isRead = false;
    }

    /**
     * Constructor with all required fields
     * @param userId The user ID
     * @param message The notification message
     * @param type The notification type
     */
    public Notification(Long userId, String message, String type) {
        this();
        this.userId = userId;
        this.message = message;
        this.type = type;
    }

    /**
     * Constructor with isRead parameter
     * @param userId The user ID
     * @param message The notification message
     * @param type The notification type
     * @param isRead Whether the notification has been read
     */
    public Notification(Long userId, String message, String type, Boolean isRead) {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.isRead = isRead != null ? isRead : false;
    }

    /**
     * Mark notification as read
     */
    public void markAsRead() {
        this.isRead = true;
    }

    /**
     * Mark notification as unread
     */
    public void markAsUnread() {
        this.isRead = false;
    }
}
