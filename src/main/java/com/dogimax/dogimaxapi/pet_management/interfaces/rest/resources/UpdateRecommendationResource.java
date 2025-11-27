package com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Update Recommendation resource
 * @param type the type of recommendation
 * @param title the title
 * @param description the description
 * @param priority the priority level
 * @param expirationDate the expiration date
 * @param isCompleted whether the recommendation is completed
 * @param completionDate the completion date
 * @param aiSource the AI source
 * @param confidence the confidence level
 * @param parameters additional parameters
 */
public record UpdateRecommendationResource(
        String type,
        String title,
        String description,
        String priority,
        LocalDateTime expirationDate,
        Boolean isCompleted,
        LocalDateTime completionDate,
        String aiSource,
        Double confidence,
        String parameters
) {
}
