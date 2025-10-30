package com.dogimax.dogimaxapi.appointments.domain.model.aggregates;

import com.dogimax.dogimaxapi.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * veterinary aggregate root.
 * Represents a veterinary clinic in the system.
 */
@Entity
@Table(name = "veterinarys")
@Getter
@Setter
public class veterinary extends AuditableAbstractAggregateRoot<veterinary> {

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "direccion", nullable = false, length = 500)
    private String direccion;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "veterinary_servicios", joinColumns = @JoinColumn(name = "veterinary_id"))
    @Column(name = "servicio")
    private List<String> servicios = new ArrayList<>();

    @Column(name = "horario", nullable = false, length = 100)
    private String horario;

    /**
     * Default constructor
     */
    public veterinary() {
        this.servicios = new ArrayList<>();
    }

    /**
     * Constructor with all required fields
     * @param nombre The veterinary clinic name
     * @param direccion The address
     * @param telefono The phone number
     * @param servicios The list of services
     * @param horario The working hours
     */
    public veterinary(String nombre, String direccion, String telefono, List<String> servicios, String horario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.servicios = servicios != null ? servicios : new ArrayList<>();
        this.horario = horario;
    }
}
