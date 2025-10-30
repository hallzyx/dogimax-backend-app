package com.dogimax.dogimaxapi.appointments.interfaces.rest;

import com.dogimax.dogimaxapi.appointments.domain.model.commands.DeleteveterinaryCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAllveterinarysQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetveterinaryByIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.services.VeterinaryCommandService;
import com.dogimax.dogimaxapi.appointments.domain.services.veterinaryQueryService;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.CreateveterinaryResource;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.UpdateveterinaryResource;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.veterinaryResource;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.transform.CreateveterinaryCommandFromResourceAssembler;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.transform.UpdateveterinaryCommandFromResourceAssembler;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.transform.veterinaryResourceFromEntityAssembler;
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
 * veterinarys controller
 */
@RestController
@RequestMapping(value = "/api/v1/veterinarys", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "veterinarys", description = "Available veterinary Endpoints")
public class veterinarysController {

    private final VeterinaryCommandService veterinaryCommandService;
    private final veterinaryQueryService veterinaryQueryService;

    public veterinarysController(VeterinaryCommandService veterinaryCommandService,
                                  veterinaryQueryService veterinaryQueryService) {
        this.veterinaryCommandService = veterinaryCommandService;
        this.veterinaryQueryService = veterinaryQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all veterinarys", description = "Retrieve all veterinary clinics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "veterinarys retrieved successfully")
    })
    public ResponseEntity<List<veterinaryResource>> getAllveterinarys() {
        var query = new GetAllveterinarysQuery();
        var veterinarys = veterinaryQueryService.handle(query);
        var resources = veterinarys.stream()
                .map(veterinaryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{veterinaryId}")
    @Operation(summary = "Get veterinary by id", description = "Retrieve a veterinary clinic by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "veterinary retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "veterinary not found")
    })
    public ResponseEntity<veterinaryResource> getveterinaryById(@PathVariable Long veterinaryId) {
        var query = new GetveterinaryByIdQuery(veterinaryId);
        var veterinary = veterinaryQueryService.handle(query);
        if (veterinary.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = veterinaryResourceFromEntityAssembler.toResourceFromEntity(veterinary.get());
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    @Operation(summary = "Create veterinary", description = "Create a new veterinary clinic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "veterinary created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<veterinaryResource> createveterinary(@RequestBody CreateveterinaryResource resource) {
        var command = CreateveterinaryCommandFromResourceAssembler.toCommandFromResource(resource);
        var veterinary = veterinaryCommandService.handle(command);
        if (veterinary.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var veterinaryResource = veterinaryResourceFromEntityAssembler.toResourceFromEntity(veterinary.get());
        return new ResponseEntity<>(veterinaryResource, HttpStatus.CREATED);
    }

    @PutMapping("/{veterinaryId}")
    @Operation(summary = "Update veterinary", description = "Update an existing veterinary clinic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "veterinary updated successfully"),
            @ApiResponse(responseCode = "404", description = "veterinary not found")
    })
    public ResponseEntity<veterinaryResource> updateveterinary(
            @PathVariable Long veterinaryId,
            @RequestBody UpdateveterinaryResource resource) {
        var command = UpdateveterinaryCommandFromResourceAssembler.toCommandFromResource(resource, veterinaryId);
        var veterinary = veterinaryCommandService.handle(command);
        if (veterinary.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var veterinaryResource = veterinaryResourceFromEntityAssembler.toResourceFromEntity(veterinary.get());
        return ResponseEntity.ok(veterinaryResource);
    }

    @DeleteMapping("/{veterinaryId}")
    @Operation(summary = "Delete veterinary", description = "Delete a veterinary clinic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "veterinary deleted successfully"),
            @ApiResponse(responseCode = "404", description = "veterinary not found")
    })
    public ResponseEntity<Void> deleteveterinary(@PathVariable Long veterinaryId) {
        var command = new DeleteveterinaryCommand(veterinaryId);
        try {
            veterinaryCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
