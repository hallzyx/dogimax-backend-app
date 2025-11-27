package com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Create Recommendation resource
 * @param petId the pet id
 * @param type the type of recommendation
 * @param title the title
 * @param description the description
 * @param priority the priority level
 * @param generationDate the generation date
 * @param expirationDate the expiration date
 * @param aiSource the AI source
 * @param confidence the confidence level
 * @param parameters additional parameters
 */
public record CreateRecommendationResource(
        Long petId,
        String type,
        String title,
        String description,
        String priority,
        LocalDateTime generationDate,
        LocalDateTime expirationDate,
        String aiSource,
        Double confidence,
        String parameters
) {
}
