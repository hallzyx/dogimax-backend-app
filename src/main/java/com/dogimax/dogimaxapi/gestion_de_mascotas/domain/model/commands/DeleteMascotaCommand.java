package com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands;

/**
 * Command to delete a mascota
 * @param id the mascota's id
 */
public record DeleteMascotaCommand(Long id) {
}

