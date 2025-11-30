package com.dogimax.dogimaxapi.appointments.interfaces.rest;

import com.dogimax.dogimaxapi.appointments.domain.model.commands.DeleteAppointmentCommand;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.*;
import com.dogimax.dogimaxapi.appointments.domain.services.AppointmentCommandService;
import com.dogimax.dogimaxapi.appointments.domain.services.AppointmentQueryService;
import com.dogimax.dogimaxapi.appointments.domain.services.veterinaryQueryService;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.AppointmentResource;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.CreateAppointmentResource;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.resources.UpdateAppointmentResource;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.transform.AppointmentResourceFromEntityAssembler;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.transform.CreateAppointmentCommandFromResourceAssembler;
import com.dogimax.dogimaxapi.appointments.interfaces.rest.transform.UpdateAppointmentCommandFromResourceAssembler;
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
 * Appointments controller
 * This controller exposes the endpoints to manage appointments
 */
@RestController
@RequestMapping(value = "/api/v1/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Appointments", description = "Available Appointment Endpoints")
public class AppointmentsController {

    private final AppointmentCommandService appointmentCommandService;
    private final AppointmentQueryService appointmentQueryService;
    private final veterinaryQueryService veterinaryQueryService;

    public AppointmentsController(AppointmentCommandService appointmentCommandService,
                                 AppointmentQueryService appointmentQueryService,
                                 veterinaryQueryService veterinaryQueryService) {
        this.appointmentCommandService = appointmentCommandService;
        this.appointmentQueryService = appointmentQueryService;
        this.veterinaryQueryService = veterinaryQueryService;
    }

    /**
     * Get all appointments
     * @return List of all appointments
     */
    @GetMapping
    @Operation(summary = "Get all appointments", description = "Retrieve all appointments from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    })
    public ResponseEntity<List<AppointmentResource>> getAllAppointments() {
        var getAllAppointmentsQuery = new GetAllAppointmentsQuery();
        var appointments = appointmentQueryService.handle(getAllAppointmentsQuery);
        var appointmentResources = appointments.stream()
                .map(appointment -> {
                    var veterinary = veterinaryQueryService.handle(new GetveterinaryByIdQuery(appointment.getVeterinaryId()))
                            .orElse(null);
                    return AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment, veterinary);
                })
                .toList();
        return ResponseEntity.ok(appointmentResources);
    }

    /**
     * Get appointment by id
     * @param appointmentId The appointment id
     * @return The appointment resource
     */
    @GetMapping("/{appointmentId}")
    @Operation(summary = "Get appointment by id", description = "Retrieve an appointment by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    public ResponseEntity<AppointmentResource> getAppointmentById(@PathVariable Long appointmentId) {
        var getAppointmentByIdQuery = new GetAppointmentByIdQuery(appointmentId);
        var appointment = appointmentQueryService.handle(getAppointmentByIdQuery);
        if (appointment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var veterinary = veterinaryQueryService.handle(new GetveterinaryByIdQuery(appointment.get().getVeterinaryId()))
                .orElse(null);
        var appointmentResource = AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment.get(), veterinary);
        return ResponseEntity.ok(appointmentResource);
    }

    /**
     * Get appointments by mascota id
     * @param mascotaId The pet id
     * @return List of appointments for the pet
     */
    @GetMapping("/mascota/{mascotaId}")
    @Operation(summary = "Get appointments by pet", description = "Retrieve all appointments for a specific pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    })
    public ResponseEntity<List<AppointmentResource>> getAppointmentsByMascotaId(@PathVariable Long mascotaId) {
        var query = new GetAppointmentsByMascotaIdQuery(mascotaId);
        var appointments = appointmentQueryService.handle(query);
        var appointmentResources = appointments.stream()
                .map(appointment -> {
                    var veterinary = veterinaryQueryService.handle(new GetveterinaryByIdQuery(appointment.getVeterinaryId()))
                            .orElse(null);
                    return AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment, veterinary);
                })
                .toList();
        return ResponseEntity.ok(appointmentResources);
    }

    /**
     * Get appointments by veterinary id
     * @param veterinaryId The veterinary clinic id
     * @return List of appointments for the veterinary clinic
     */
    @GetMapping("/veterinary/{veterinaryId}")
    @Operation(summary = "Get appointments by veterinary clinic", description = "Retrieve all appointments for a specific veterinary clinic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    })
    public ResponseEntity<List<AppointmentResource>> getAppointmentsByveterinaryId(@PathVariable Long veterinaryId) {
        var query = new GetAppointmentsByveterinaryIdQuery(veterinaryId);
        var appointments = appointmentQueryService.handle(query);
        var veterinary = veterinaryQueryService.handle(new GetveterinaryByIdQuery(veterinaryId))
                .orElse(null);
        var appointmentResources = appointments.stream()
                .map(appointment -> AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment, veterinary))
                .toList();
        return ResponseEntity.ok(appointmentResources);
    }

    /**
     * Get appointments by pet owner id
     * @param petOwnerId The pet owner (user) id
     * @return List of appointments for all pets owned by the user
     */
    @GetMapping("/pet-owner/{petOwnerId}")
    @Operation(summary = "Get appointments by pet owner", description = "Retrieve all appointments for pets owned by a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    })
    public ResponseEntity<List<AppointmentResource>> getAppointmentsByPetOwnerId(@PathVariable Long petOwnerId) {
        var query = new GetAppointmentsByPetOwnerIdQuery(petOwnerId);
        var appointments = appointmentQueryService.handle(query);
        var appointmentResources = appointments.stream()
                .map(appointment -> {
                    var veterinary = veterinaryQueryService.handle(new GetveterinaryByIdQuery(appointment.getVeterinaryId()))
                            .orElse(null);
                    return AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment, veterinary);
                })
                .toList();
        return ResponseEntity.ok(appointmentResources);
    }

    /**
     * Create a new appointment
     * @param createAppointmentResource The appointment data
     * @return The created appointment
     */
    @PostMapping
    @Operation(summary = "Create appointment", description = "Create a new appointment in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid appointment data")
    })
    public ResponseEntity<AppointmentResource> createAppointment(
            @RequestBody CreateAppointmentResource createAppointmentResource) {
        var createAppointmentCommand = 
                CreateAppointmentCommandFromResourceAssembler.toCommandFromResource(createAppointmentResource);
        var appointment = appointmentCommandService.handle(createAppointmentCommand);
        if (appointment.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var veterinary = veterinaryQueryService.handle(new GetveterinaryByIdQuery(appointment.get().getVeterinaryId()))
                .orElse(null);
        var appointmentResource = AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment.get(), veterinary);
        return new ResponseEntity<>(appointmentResource, HttpStatus.CREATED);
    }

    /**
     * Update an existing appointment
     * @param appointmentId The appointment id
     * @param updateAppointmentResource The updated appointment data
     * @return The updated appointment
     */
    @PutMapping("/{appointmentId}")
    @Operation(summary = "Update appointment", description = "Update an existing appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid appointment data")
    })
    public ResponseEntity<AppointmentResource> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody UpdateAppointmentResource updateAppointmentResource) {
        var updateAppointmentCommand = 
                UpdateAppointmentCommandFromResourceAssembler.toCommandFromResource(updateAppointmentResource, appointmentId);
        var appointment = appointmentCommandService.handle(updateAppointmentCommand);
        if (appointment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var veterinary = veterinaryQueryService.handle(new GetveterinaryByIdQuery(appointment.get().getVeterinaryId()))
                .orElse(null);
        var appointmentResource = AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment.get(), veterinary);
        return ResponseEntity.ok(appointmentResource);
    }

    /**
     * Delete an appointment
     * @param appointmentId The appointment id
     * @return No content
     */
    @DeleteMapping("/{appointmentId}")
    @Operation(summary = "Delete appointment", description = "Delete an appointment from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) {
        var deleteAppointmentCommand = new DeleteAppointmentCommand(appointmentId);
        try {
            appointmentCommandService.handle(deleteAppointmentCommand);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update veterinary status of an appointment (accept or reject)
     * @param appointmentId The appointment id
     * @param veterinaryStatus The new veterinary status (ACCEPTED or REJECTED)
     * @return The updated appointment
     */
    @PatchMapping("/{appointmentId}/veterinary-status")
    @Operation(summary = "Update veterinary status", description = "Accept or reject an appointment request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veterinary status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid status")
    })
    public ResponseEntity<AppointmentResource> updateVeterinaryStatus(
            @PathVariable Long appointmentId,
            @RequestParam String veterinaryStatus) {
        var command = new com.dogimax.dogimaxapi.appointments.domain.model.commands.UpdateAppointmentVeterinaryStatusCommand(
                appointmentId, veterinaryStatus);
        var appointment = appointmentCommandService.handle(command);
        if (appointment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var veterinary = veterinaryQueryService.handle(new GetveterinaryByIdQuery(appointment.get().getVeterinaryId()))
                .orElse(null);
        var appointmentResource = AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment.get(), veterinary);
        return ResponseEntity.ok(appointmentResource);
    }
}
