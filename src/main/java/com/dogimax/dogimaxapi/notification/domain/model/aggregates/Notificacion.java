package com.dogimax.dogimaxapi.notification.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter

public class Notificacion extends AuditableAbstractAggregateRoot<Notificacion> {

    private String titulo;

    private String mensaje;

    @Enumerated(EnumType.STRING)
    private Canal canal; // Ej. EMAIL, PUSH, SMS

    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado;

    private LocalDateTime fechaProgramada;

    private LocalDateTime fechaEnviada;

    // --- Referencias a otros Bounded Contexts ---
    private Long destinatarioId; // Del BContext de IAM (Usuario)

    public Notificacion() {
        super();
        this.estado = EstadoEnvio.PENDIENTE;
    }

    public Notificacion(String titulo, String mensaje, Canal canal, Long destinatarioId, LocalDateTime fechaProgramada) {
        this();
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.canal = canal;
        this.destinatarioId = destinatarioId;
        this.fechaProgramada = fechaProgramada;
    }

    // --- Lógica de Dominio (Métodos) ---

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

// Enums para soporte
enum Canal {
    EMAIL,
    PUSH,
    SMS
}

enum EstadoEnvio {
    PENDIENTE,
    ENVIADA,
    FALLIDA
}
