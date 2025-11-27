package com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Recomendacion aggregate root
 * Represents a recommendation for a pet in the system
 */
@Entity
@Table(name = "recomendaciones")
@Getter
@Setter
public class Recomendacion extends AuditableAbstractAggregateRoot<Recomendacion> {

    @NotNull
    @Column(name = "mascota_id", nullable = false)
    private Long mascotaId;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 50)
    private TipoRecomendacion tipo;

    @NotBlank
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @NotBlank
    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false, length = 20)
    private Prioridad prioridad;

    @NotNull
    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    @Column(name = "completada", nullable = false)
    private Boolean completada;

    @Column(name = "fecha_completada")
    private LocalDateTime fechaCompletada;

    @Column(name = "fuente_ia", length = 100)
    private String fuenteIA;

    @Column(name = "confianza")
    private Double confianza;

    @Column(name = "parametros", columnDefinition = "TEXT")
    private String parametros;

    /**
     * Enum for recommendation types
     */
    public enum TipoRecomendacion {
        ALIMENTACION,
        EJERCICIO,
        SALUD,
        COMPORTAMIENTO,
        CUIDADOS,
        VACUNACION
    }

    /**
     * Enum for priority levels
     */
    public enum Prioridad {
        BAJA,
        MEDIA,
        ALTA,
        CRITICA
    }

    /**
     * Default constructor
     */
    public Recomendacion() {
        super();
        this.completada = false;
        this.fechaGeneracion = LocalDateTime.now();
    }

    /**
     * Constructor with required fields
     * @param mascotaId The pet ID
     * @param tipo The recommendation type
     * @param titulo The title
     * @param descripcion The description
     * @param prioridad The priority level
     */
    public Recomendacion(Long mascotaId, TipoRecomendacion tipo, String titulo, 
                        String descripcion, Prioridad prioridad) {
        this();
        this.mascotaId = mascotaId;
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    /**
     * Constructor with all fields
     * @param mascotaId The pet ID
     * @param tipo The recommendation type
     * @param titulo The title
     * @param descripcion The description
     * @param prioridad The priority level
     * @param fechaVencimiento Expiration date
     * @param fuenteIA AI source
     * @param confianza Confidence level
     * @param parametros Additional parameters as JSON string
     */
    public Recomendacion(Long mascotaId, TipoRecomendacion tipo, String titulo, String descripcion, 
                        Prioridad prioridad, LocalDateTime fechaVencimiento, String fuenteIA, 
                        Double confianza, String parametros) {
        this(mascotaId, tipo, titulo, descripcion, prioridad);
        this.fechaVencimiento = fechaVencimiento;
        this.fuenteIA = fuenteIA;
        this.confianza = confianza;
        this.parametros = parametros;
    }

    /**
     * Mark recommendation as completed
     */
    public void marcarComoCompletada() {
        this.completada = true;
        this.fechaCompletada = LocalDateTime.now();
    }

    /**
     * Mark recommendation as not completed
     */
    public void marcarComoNoCompletada() {
        this.completada = false;
        this.fechaCompletada = null;
    }
}
