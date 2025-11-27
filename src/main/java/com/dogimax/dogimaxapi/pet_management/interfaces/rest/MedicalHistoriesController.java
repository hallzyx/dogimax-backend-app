package com.dogimax.dogimaxapi.pet_management.interfaces.rest;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.MedicalHistory;
import com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories.MedicalHistoryRepository;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.CreateMedicalHistoryResource;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.MedicalHistoryResource;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.UpdateMedicalHistoryResource;
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
 * REST controller for medical histories
 */
@RestController
@RequestMapping(value = "/api/v1/medical-histories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Medical Histories", description = "Available Medical History Endpoints")
public class MedicalHistoriesController {

    private final MedicalHistoryRepository medicalHistoryRepository;

    public MedicalHistoriesController(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    /**
     * Get all medical histories
     */
    @GetMapping
    @Operation(summary = "Get all medical histories", description = "Get all medical histories in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical histories retrieved successfully")})
    public ResponseEntity<List<MedicalHistoryResource>> getAllMedicalHistories(
            @RequestParam(required = false) Long petId) {
        List<MedicalHistory> histories;
        if (petId != null) {
            histories = medicalHistoryRepository.findByPetId(petId);
        } else {
            histories = medicalHistoryRepository.findAll();
        }
        var resources = histories.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    /**
     * Get medical history by id
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get medical history by id", description = "Get a medical history by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical history retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Medical history not found")})
    public ResponseEntity<MedicalHistoryResource> getMedicalHistoryById(@PathVariable Long id) {
        return medicalHistoryRepository.findById(id)
                .map(this::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new medical history
     */
    @PostMapping
    @Operation(summary = "Create medical history", description = "Create a new medical history record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medical history created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")})
    public ResponseEntity<MedicalHistoryResource> createMedicalHistory(
            @Valid @RequestBody CreateMedicalHistoryResource resource) {
        var medicalHistory = new MedicalHistory();
        medicalHistory.setPetId(resource.petId());
        medicalHistory.setRegistrationDate(resource.registrationDate() != null ? 
                resource.registrationDate() : LocalDateTime.now());
        medicalHistory.setRecordType(MedicalHistory.RecordType.valueOf(resource.recordType()));
        medicalHistory.setDescription(resource.description());
        medicalHistory.setVeterinarian(resource.veterinarian());
        medicalHistory.setObservations(resource.observations());
        medicalHistory.setFiles(resource.files());
        medicalHistory.setCost(resource.cost());
        medicalHistory.setNextAppointment(resource.nextAppointment());
        
        var saved = medicalHistoryRepository.save(medicalHistory);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResource(saved));
    }

    /**
     * Update a medical history
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update medical history", description = "Update an existing medical history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical history updated successfully"),
            @ApiResponse(responseCode = "404", description = "Medical history not found")})
    public ResponseEntity<MedicalHistoryResource> updateMedicalHistory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMedicalHistoryResource resource) {
        return medicalHistoryRepository.findById(id)
                .map(medicalHistory -> {
                    medicalHistory.setRegistrationDate(resource.registrationDate());
                    medicalHistory.setRecordType(MedicalHistory.RecordType.valueOf(resource.recordType()));
                    medicalHistory.setDescription(resource.description());
                    medicalHistory.setVeterinarian(resource.veterinarian());
                    medicalHistory.setObservations(resource.observations());
                    medicalHistory.setFiles(resource.files());
                    medicalHistory.setCost(resource.cost());
                    medicalHistory.setNextAppointment(resource.nextAppointment());
                    return medicalHistoryRepository.save(medicalHistory);
                })
                .map(this::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a medical history
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete medical history", description = "Delete a medical history record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medical history deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Medical history not found")})
    public ResponseEntity<Void> deleteMedicalHistory(@PathVariable Long id) {
        if (!medicalHistoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicalHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convert entity to resource
     */
    private MedicalHistoryResource toResource(MedicalHistory medicalHistory) {
        return new MedicalHistoryResource(
                medicalHistory.getId(),
                medicalHistory.getPetId(),
                medicalHistory.getRegistrationDate(),
                medicalHistory.getRecordType().name(),
                medicalHistory.getDescription(),
                medicalHistory.getVeterinarian(),
                medicalHistory.getObservations(),
                medicalHistory.getFiles(),
                medicalHistory.getCost(),
                medicalHistory.getNextAppointment()
        );
    }
}
