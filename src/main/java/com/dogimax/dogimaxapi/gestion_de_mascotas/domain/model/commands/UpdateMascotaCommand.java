package com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates.Mascota;

import java.time.LocalDate;

/**
 * Command to update a mascota
 * @param id the mascota's id
 * @param nombre the mascota's name
 * @param especie the mascota's species
 * @param raza the mascota's breed
 * @param fechaNacimiento the mascota's birth date
 * @param sexo the mascota's gender
 * @param peso the mascota's weight
 * @param unidadPeso the weight unit
 * @param descripcion the mascota's description
 * @param fotoUrl the mascota's photo URL
 */
public record UpdateMascotaCommand(
        Long id,
        String nombre,
        String especie,
        String raza,
        LocalDate fechaNacimiento,
        Mascota.Sexo sexo,
        Double peso,
        String unidadPeso,
        String descripcion,
        String fotoUrl
) {
}

