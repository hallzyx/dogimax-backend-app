package com.dogimax.dogimaxapi.pet_management.interfaces.rest;

import com.dogimax.dogimaxapi.pet_management.domain.model.commands.DeletePetCommand;
import com.dogimax.dogimaxapi.pet_management.domain.model.queries.GetAllPetsQuery;
import com.dogimax.dogimaxapi.pet_management.domain.model.queries.GetPetByIdQuery;
import com.dogimax.dogimaxapi.pet_management.domain.model.queries.GetPetsByUserIdQuery;
import com.dogimax.dogimaxapi.pet_management.domain.services.PetCommandService;
import com.dogimax.dogimaxapi.pet_management.domain.services.PetQueryService;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.CreatePetResource;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.PetResource;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.resources.UpdatePetResource;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.transform.CreatePetCommandFromResourceAssembler;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.transform.PetResourceFromEntityAssembler;
import com.dogimax.dogimaxapi.pet_management.interfaces.rest.transform.UpdatePetCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a REST controller that exposes the pets resource.
 * It includes the following operations:
 * - GET /api/v1/pets: returns all the pets
 * - GET /api/v1/pets/{petId}: returns the pet with the given id
 * - GET /api/v1/pets/user/{userId}: returns all pets for a given user
 * - POST /api/v1/pets: creates a new pet
 * - PUT /api/v1/pets/{petId}: updates the pet with the given id
 * - DELETE /api/v1/pets/{petId}: deletes (deactivates) the pet with the given id
 **/
@RestController
@RequestMapping(value = "/api/v1/pets", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Pets", description = "Available Pet Endpoints")
public class PetsController {
    
    private final PetCommandService petCommandService;
    private final PetQueryService petQueryService;

    public PetsController(PetCommandService petCommandService, PetQueryService petQueryService) {
        this.petCommandService = petCommandService;
        this.petQueryService = petQueryService;
    }

    /**
     * This method returns all the pets.
     * @return a list of pet resources
     * @see PetResource
     */
    @GetMapping
    @Operation(summary = "Get all pets", description = "Get all the pets available in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pets retrieved successfully.")})
    public ResponseEntity<List<PetResource>> getAllMascotas() {
        var getAllMascotasQuery = new GetAllPetsQuery();
        var pets = petQueryService.handle(getAllMascotasQuery);
        var petResources = pets.stream()
                .map(PetResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(petResources);
    }

    /**
     * This method returns the pet with the given id.
     * @param petId the pet id
     * @return the pet resource with the given id
     * @see PetResource
     */
    @GetMapping(value = "/{petId}")
    @Operation(summary = "Get pet by id", description = "Get the pet with the given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Pet not found.")})
    public ResponseEntity<PetResource> getMascotaById(@PathVariable Long petId) {
        var getMascotaByIdQuery = new GetPetByIdQuery(petId);
        var pet = petQueryService.handle(getMascotaByIdQuery);
        if (pet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var petResource = PetResourceFromEntityAssembler.toResourceFromEntity(pet.get());
        return ResponseEntity.ok(petResource);
    }

    /**
     * This method returns all pets for a given user.
     * @param userId the user id
     * @return a list of pet resources for the given user
     * @see PetResource
     */
    @GetMapping(value = "/user/{userId}")
    @Operation(summary = "Get pets by user id", description = "Get all pets for a given user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pets retrieved successfully.")})
    public ResponseEntity<List<PetResource>> getMascotasByUserId(@PathVariable Long userId) {
        var getMascotasByUserIdQuery = new GetPetsByUserIdQuery(userId);
        var pets = petQueryService.handle(getMascotasByUserIdQuery);
        var petResources = pets.stream()
                .map(PetResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(petResources);
    }

    /**
     * This method creates a new pet.
     * @param resource the create pet resource
     * @return the created pet resource
     * @see CreatePetResource
     * @see PetResource
     */
    @PostMapping
    @Operation(summary = "Create a pet", description = "Create a new pet in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")})
    public ResponseEntity<PetResource> createMascota(@Valid @RequestBody CreatePetResource resource) {
        var command = CreatePetCommandFromResourceAssembler.toCommandFromResource(resource);
        var pet = petCommandService.handle(command);
        var petResource = PetResourceFromEntityAssembler.toResourceFromEntity(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(petResource);
    }

    /**
     * This method updates the pet with the given id.
     * @param petId the pet id
     * @param resource the update pet resource
     * @return the updated pet resource
     * @see UpdatePetResource
     * @see PetResource
     */
    @PutMapping(value = "/{petId}")
    @Operation(summary = "Update a pet", description = "Update the pet with the given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully."),
            @ApiResponse(responseCode = "404", description = "Pet not found.")})
    public ResponseEntity<PetResource> updateMascota(
            @PathVariable Long petId,
            @Valid @RequestBody UpdatePetResource resource) {
        var command = UpdatePetCommandFromResourceAssembler.toCommandFromResource(petId, resource);
        var pet = petCommandService.handle(command);
        if (pet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var petResource = PetResourceFromEntityAssembler.toResourceFromEntity(pet.get());
        return ResponseEntity.ok(petResource);
    }

    /**
     * This method deletes (deactivates) the pet with the given id.
     * @param petId the pet id
     * @return no content response
     */
    @DeleteMapping(value = "/{petId}")
    @Operation(summary = "Delete a pet", description = "Delete (deactivate) the pet with the given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Pet not found.")})
    public ResponseEntity<Void> deleteMascota(@PathVariable Long petId) {
        var command = new DeletePetCommand(petId);
        petCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}

