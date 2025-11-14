package com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands.DeleteMascotaCommand;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.queries.GetAllMascotasQuery;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.queries.GetMascotaByIdQuery;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.queries.GetMascotasByUserIdQuery;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.services.MascotaCommandService;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.services.MascotaQueryService;
import com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.resources.CreateMascotaResource;
import com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.resources.MascotaResource;
import com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.resources.UpdateMascotaResource;
import com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.transform.CreateMascotaCommandFromResourceAssembler;
import com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.transform.MascotaResourceFromEntityAssembler;
import com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.transform.UpdateMascotaCommandFromResourceAssembler;
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
 * This class is a REST controller that exposes the mascotas resource.
 * It includes the following operations:
 * - GET /api/v1/mascotas: returns all the mascotas
 * - GET /api/v1/mascotas/{mascotaId}: returns the mascota with the given id
 * - GET /api/v1/mascotas/user/{userId}: returns all mascotas for a given user
 * - POST /api/v1/mascotas: creates a new mascota
 * - PUT /api/v1/mascotas/{mascotaId}: updates the mascota with the given id
 * - DELETE /api/v1/mascotas/{mascotaId}: deletes (deactivates) the mascota with the given id
 **/
@RestController
@RequestMapping(value = "/api/v1/mascotas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Mascotas", description = "Available Mascota Endpoints")
public class MascotasController {
    
    private final MascotaCommandService mascotaCommandService;
    private final MascotaQueryService mascotaQueryService;

    public MascotasController(MascotaCommandService mascotaCommandService, MascotaQueryService mascotaQueryService) {
        this.mascotaCommandService = mascotaCommandService;
        this.mascotaQueryService = mascotaQueryService;
    }

    /**
     * This method returns all the mascotas.
     * @return a list of mascota resources
     * @see MascotaResource
     */
    @GetMapping
    @Operation(summary = "Get all mascotas", description = "Get all the mascotas available in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascotas retrieved successfully.")})
    public ResponseEntity<List<MascotaResource>> getAllMascotas() {
        var getAllMascotasQuery = new GetAllMascotasQuery();
        var mascotas = mascotaQueryService.handle(getAllMascotasQuery);
        var mascotaResources = mascotas.stream()
                .map(MascotaResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(mascotaResources);
    }

    /**
     * This method returns the mascota with the given id.
     * @param mascotaId the mascota id
     * @return the mascota resource with the given id
     * @see MascotaResource
     */
    @GetMapping(value = "/{mascotaId}")
    @Operation(summary = "Get mascota by id", description = "Get the mascota with the given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Mascota not found.")})
    public ResponseEntity<MascotaResource> getMascotaById(@PathVariable Long mascotaId) {
        var getMascotaByIdQuery = new GetMascotaByIdQuery(mascotaId);
        var mascota = mascotaQueryService.handle(getMascotaByIdQuery);
        if (mascota.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var mascotaResource = MascotaResourceFromEntityAssembler.toResourceFromEntity(mascota.get());
        return ResponseEntity.ok(mascotaResource);
    }

    /**
     * This method returns all mascotas for a given user.
     * @param userId the user id
     * @return a list of mascota resources for the given user
     * @see MascotaResource
     */
    @GetMapping(value = "/user/{userId}")
    @Operation(summary = "Get mascotas by user id", description = "Get all mascotas for a given user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascotas retrieved successfully.")})
    public ResponseEntity<List<MascotaResource>> getMascotasByUserId(@PathVariable Long userId) {
        var getMascotasByUserIdQuery = new GetMascotasByUserIdQuery(userId);
        var mascotas = mascotaQueryService.handle(getMascotasByUserIdQuery);
        var mascotaResources = mascotas.stream()
                .map(MascotaResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(mascotaResources);
    }

    /**
     * This method creates a new mascota.
     * @param resource the create mascota resource
     * @return the created mascota resource
     * @see CreateMascotaResource
     * @see MascotaResource
     */
    @PostMapping
    @Operation(summary = "Create a mascota", description = "Create a new mascota in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mascota created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")})
    public ResponseEntity<MascotaResource> createMascota(@Valid @RequestBody CreateMascotaResource resource) {
        var command = CreateMascotaCommandFromResourceAssembler.toCommandFromResource(resource);
        var mascota = mascotaCommandService.handle(command);
        var mascotaResource = MascotaResourceFromEntityAssembler.toResourceFromEntity(mascota);
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaResource);
    }

    /**
     * This method updates the mascota with the given id.
     * @param mascotaId the mascota id
     * @param resource the update mascota resource
     * @return the updated mascota resource
     * @see UpdateMascotaResource
     * @see MascotaResource
     */
    @PutMapping(value = "/{mascotaId}")
    @Operation(summary = "Update a mascota", description = "Update the mascota with the given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota updated successfully."),
            @ApiResponse(responseCode = "404", description = "Mascota not found.")})
    public ResponseEntity<MascotaResource> updateMascota(
            @PathVariable Long mascotaId,
            @Valid @RequestBody UpdateMascotaResource resource) {
        var command = UpdateMascotaCommandFromResourceAssembler.toCommandFromResource(mascotaId, resource);
        var mascota = mascotaCommandService.handle(command);
        if (mascota.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var mascotaResource = MascotaResourceFromEntityAssembler.toResourceFromEntity(mascota.get());
        return ResponseEntity.ok(mascotaResource);
    }

    /**
     * This method deletes (deactivates) the mascota with the given id.
     * @param mascotaId the mascota id
     * @return no content response
     */
    @DeleteMapping(value = "/{mascotaId}")
    @Operation(summary = "Delete a mascota", description = "Delete (deactivate) the mascota with the given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mascota deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Mascota not found.")})
    public ResponseEntity<Void> deleteMascota(@PathVariable Long mascotaId) {
        var command = new DeleteMascotaCommand(mascotaId);
        mascotaCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}

