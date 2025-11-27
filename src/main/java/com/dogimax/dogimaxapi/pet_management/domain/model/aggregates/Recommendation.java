package com.dogimax.dogimaxapi.pet_management.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Recommendation aggregate root
 * Represents a recommendation for a pet in the system
 */
@Entity
@Table(name = "recommendations")
@Getter
@Setter
public class Recommendation extends AuditableAbstractAggregateRoot<Recommendation> {

    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private RecommendationType type;

    @NotBlank
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NotBlank
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 20)
    private Priority priority;

    @NotNull
    @Column(name = "generation_date", nullable = false)
    private LocalDateTime generationDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name = "ai_source", length = 100)
    private String aiSource;

    @Column(name = "confidence")
    private Double confidence;

    @Column(name = "parameters", columnDefinition = "TEXT")
    private String parameters;

    /**
     * Enum for recommendation types
     */
    public enum RecommendationType {
        NUTRITION,
        EXERCISE,
        HEALTH,
        BEHAVIOR,
        CARE,
        VACCINATION
    }

    /**
     * Enum for priority levels
     */
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    /**
     * Default constructor
     */
    public Recommendation() {
        super();
        this.isCompleted = false;
        this.generationDate = LocalDateTime.now();
    }

    /**
     * Constructor with required fields
     * @param petId The pet ID
     * @param type The recommendation type
     * @param title The title
     * @param description The description
     * @param priority The priority level
     */
    public Recommendation(Long petId, RecommendationType type, String title, 
                        String description, Priority priority) {
        this();
        this.petId = petId;
        this.type = type;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Constructor with all fields
     * @param petId The pet ID
     * @param type The recommendation type
     * @param title The title
     * @param description The description
     * @param priority The priority level
     * @param expirationDate Expiration date
     * @param aiSource AI source
     * @param confidence Confidence level
     * @param parameters Additional parameters as JSON string
     */
    public Recommendation(Long petId, RecommendationType type, String title, String description, 
                        Priority priority, LocalDateTime expirationDate, String aiSource, 
                        Double confidence, String parameters) {
        this(petId, type, title, description, priority);
        this.expirationDate = expirationDate;
        this.aiSource = aiSource;
        this.confidence = confidence;
        this.parameters = parameters;
    }

    /**
     * Mark recommendation as completed
     */
    public void markAsCompleted() {
        this.isCompleted = true;
        this.completionDate = LocalDateTime.now();
    }

    /**
     * Mark recommendation as not completed
     */
    public void markAsNotCompleted() {
        this.isCompleted = false;
        this.completionDate = null;
    }
}
