package com.dogimax.dogimaxapi.pet_management.interfaces.rest;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Recommendation;
import com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories.RecommendationRepository;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.CreateRecommendationResource;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.RecommendationResource;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.UpdateRecommendationResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for recommendations
 */
@RestController
@RequestMapping(value = "/api/v1/recommendations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Recommendations", description = "Available Recommendation Endpoints")
public class RecommendationsController {

    private final RecommendationRepository recommendationRepository;

    public RecommendationsController(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    /**
     * Get all recommendations
     */
    @GetMapping
    @Operation(summary = "Get all recommendations", description = "Get all recommendations in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully")})
    public ResponseEntity<List<RecommendationResource>> getAllRecommendations(
            @RequestParam(required = false) Long petId) {
        List<Recommendation> recommendations;
        if (petId != null) {
            recommendations = recommendationRepository.findByPetId(petId);
        } else {
            recommendations = recommendationRepository.findAll();
        }
        var resources = recommendations.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    /**
     * Get recommendation by id
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get recommendation by id", description = "Get a recommendation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recommendation retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Recommendation not found")})
    public ResponseEntity<RecommendationResource> getRecommendationById(@PathVariable Long id) {
        return recommendationRepository.findById(id)
                .map(this::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new recommendation
     */
    @PostMapping
    @Operation(summary = "Create recommendation", description = "Create a new recommendation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recommendation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")})
    public ResponseEntity<RecommendationResource> createRecommendation(
            @Valid @RequestBody CreateRecommendationResource resource) {
        var recommendation = new Recommendation();
        recommendation.setPetId(resource.petId());
        recommendation.setType(Recommendation.RecommendationType.valueOf(resource.type()));
        recommendation.setTitle(resource.title());
        recommendation.setDescription(resource.description());
        recommendation.setPriority(Recommendation.Priority.valueOf(resource.priority()));
        recommendation.setGenerationDate(resource.generationDate() != null ? 
                resource.generationDate() : LocalDateTime.now());
        recommendation.setExpirationDate(resource.expirationDate());
        recommendation.setIsCompleted(false);
        recommendation.setAiSource(resource.aiSource());
        recommendation.setConfidence(resource.confidence());
        recommendation.setParameters(resource.parameters());
        
        var saved = recommendationRepository.save(recommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResource(saved));
    }

    /**
     * Update a recommendation
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update recommendation", description = "Update an existing recommendation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recommendation updated successfully"),
            @ApiResponse(responseCode = "404", description = "Recommendation not found")})
    public ResponseEntity<RecommendationResource> updateRecommendation(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRecommendationResource resource) {
        return recommendationRepository.findById(id)
                .map(recommendation -> {
                    recommendation.setType(Recommendation.RecommendationType.valueOf(resource.type()));
                    recommendation.setTitle(resource.title());
                    recommendation.setDescription(resource.description());
                    recommendation.setPriority(Recommendation.Priority.valueOf(resource.priority()));
                    recommendation.setExpirationDate(resource.expirationDate());
                    if (resource.isCompleted() != null) {
                        recommendation.setIsCompleted(resource.isCompleted());
                        if (resource.isCompleted() && resource.completionDate() != null) {
                            recommendation.setCompletionDate(resource.completionDate());
                        }
                    }
                    recommendation.setAiSource(resource.aiSource());
                    recommendation.setConfidence(resource.confidence());
                    recommendation.setParameters(resource.parameters());
                    return recommendationRepository.save(recommendation);
                })
                .map(this::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a recommendation
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete recommendation", description = "Delete a recommendation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recommendation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recommendation not found")})
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long id) {
        if (!recommendationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        recommendationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convert entity to resource
     */
    private RecommendationResource toResource(Recommendation recommendation) {
        return new RecommendationResource(
                recommendation.getId(),
                recommendation.getPetId(),
                recommendation.getType().name(),
                recommendation.getTitle(),
                recommendation.getDescription(),
                recommendation.getPriority().name(),
                recommendation.getGenerationDate(),
                recommendation.getExpirationDate(),
                recommendation.getIsCompleted(),
                recommendation.getCompletionDate(),
                recommendation.getAiSource(),
                recommendation.getConfidence(),
                recommendation.getParameters()
        );
    }
}
