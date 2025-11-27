package com.dogimax.dogimaxapi.notification.infrastructure.persistence.jpa.repositories;

import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Notification JPA repository
 * This interface extends JpaRepository
 */
@Repository
public interface NotificationJPARepository extends JpaRepository<Notification, Long> {

    /**
     * Find notifications by user id
     * @param userId The user id
     * @return List of notifications for the user
     */
    List<Notification> findByUserId(Long userId);
}

