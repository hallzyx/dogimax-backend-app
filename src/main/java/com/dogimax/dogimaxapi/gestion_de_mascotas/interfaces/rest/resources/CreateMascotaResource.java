package com.dogimax.dogimaxapi.gestion_de_mascotas.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * Create mascota resource
 * @param nombre the mascota's name
 * @param especie the mascota's species
 * @param raza the mascota's breed
 * @param fechaNacimiento the mascota's birth date
 * @param sexo the mascota's gender
 * @param peso the mascota's weight
 * @param unidadPeso the weight unit
 * @param descripcion the mascota's description
 * @param fotoUrl the mascota's photo URL
 * @param userId the owner's user id
 */
public record CreateMascotaResource(
        String nombre,
        String especie,
        String raza,
        LocalDate fechaNacimiento,
        String sexo,
        Double peso,
        String unidadPeso,
        String descripcion,
        String fotoUrl,
        Long userId
) {
}

