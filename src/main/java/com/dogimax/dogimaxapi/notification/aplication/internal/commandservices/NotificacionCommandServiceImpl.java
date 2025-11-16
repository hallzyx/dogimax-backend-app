package com.dogimax.dogimaxapi.notification.aplication.internal.commandservices;

import com.dogimax.dogimaxapi.notification.domain.model.enums.Canal;
import com.dogimax.dogimaxapi.notification.domain.model.enums.EstadoEnvio;
import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class NotificacionCommandServiceImpl {

    private String titulo;
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private Canal canal; // El campo en la BD sigue siendo un Enum

    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado;

    private LocalDateTime fechaProgramada;
    private LocalDateTime fechaEnviada;
    private Long destinatarioId;

    // --- Constructor Vacío (Requerido por JPA) ---
    public NotificacionCommandServiceImpl() {
        super();
        this.estado = EstadoEnvio.PENDIENTE;
    }

    // Acepta un String para el canal (canalString)
    public NotificacionCommandServiceImpl(String titulo, String mensaje, String canalString, Long destinatarioId, LocalDateTime fechaProgramada) {
        this(); // Llama al constructor vacío
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.destinatarioId = destinatarioId;
        this.fechaProgramada = fechaProgramada;

        // --- Lógica de conversión DENTRO del agregado ---
        // Convierte el String a Enum de forma segura
        try {
            this.canal = Canal.valueOf(canalString.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            // Lanza una excepción clara si el String no es "EMAIL", "PUSH", o "SMS"
            throw new IllegalArgumentException("Canal no válido: " + canalString);
        }
    }

    // --- Lógica de Dominio ---
    public void marcarComoEnviada() {
        if (this.estado == EstadoEnvio.PENDIENTE) {
            this.estado = EstadoEnvio.ENVIADA;
            this.fechaEnviada = LocalDateTime.now();
        }
    }

    public void marcarError() {
        this.estado = EstadoEnvio.FALLIDA;
    }
}
