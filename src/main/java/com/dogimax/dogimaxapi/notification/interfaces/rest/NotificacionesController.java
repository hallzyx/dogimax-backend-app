package com.dogimax.dogimaxapi.notification.interfaces.rest;

import com.dogimax.dogimaxapi.notification.aplication.NotificacionCommandService;
import com.dogimax.dogimaxapi.notification.aplication.NotificacionQueryService; // <-- 1. Importar QueryService
import com.dogimax.dogimaxapi.notification.domain.model.commands.CrearNotificacionCommand;
import com.dogimax.dogimaxapi.notification.domain.model.queries.GetNotificacionesByDestinatarioIdQuery; // <-- 2. Importar Query
import com.dogimax.dogimaxapi.notification.interfaces.rest.resources.CrearNotificacionResource;
import com.dogimax.dogimaxapi.notification.interfaces.rest.resources.NotificacionResource; // <-- 3. Importar Resource
import com.dogimax.dogimaxapi.notification.interfaces.rest.transform.NotificacionResourceAssembler; // <-- 4. Importar Assembler
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // <-- 5. Importar List
import java.util.stream.Collectors; // <-- 6. Importar Collectors

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionesController {

    private final NotificacionCommandService notificacionCommandService;
    private final NotificacionQueryService notificacionQueryService; // <-- 7. Inyectar QueryService

    public NotificacionesController(NotificacionCommandService notificacionCommandService, NotificacionQueryService notificacionQueryService) {
        this.notificacionCommandService = notificacionCommandService;
        this.notificacionQueryService = notificacionQueryService; // <-- 8. Inyectar en constructor
    }

    @PostMapping
    public ResponseEntity<?> createNotificacion(@RequestBody CrearNotificacionResource resource) {
        // ... (código del POST que ya teníamos) ...
        var command = new CrearNotificacionCommand(
                resource.titulo(),
                resource.mensaje(),
                resource.canal(),
                resource.destinatarioId(),
                resource.fechaProgramada()
        );
        var result = notificacionCommandService.handle(command);
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().body("No se pudo crear la notificación");
        }
        // Usamos el Assembler para retornar el DTO
        var notificacionResource = NotificacionResourceAssembler.toResourceFromEntity(result.get());
        return new ResponseEntity<>(notificacionResource, HttpStatus.CREATED);
    }

    // --- NUEVO ENDPOINT GET ---
    @GetMapping("/usuario/{destinatarioId}")
    public ResponseEntity<List<NotificacionResource>> getNotificacionesPorUsuario(@PathVariable Long destinatarioId) {

        // 1. Crear el objeto Query
        var query = new GetNotificacionesByDestinatarioIdQuery(destinatarioId);

        // 2. Ejecutar el QueryService
        var notificaciones = notificacionQueryService.handle(query);

        // 3. Convertir la lista de Agregados a una lista de DTOs
        var notificacionResources = notificaciones.stream()
                .map(NotificacionResourceAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(notificacionResources);
    }
}