package com.dogimax.dogimaxapi.gestion_de_mascotas.domain.services;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates.Mascota;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands.CreateMascotaCommand;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands.DeleteMascotaCommand;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands.UpdateMascotaCommand;

import java.util.Optional;

/**
 * Mascota command service
 * <p>
 *     This interface represents the service to handle mascota commands.
 * </p>
 */
public interface MascotaCommandService {
    /**
     * Handle create mascota command
     * @param command the {@link CreateMascotaCommand} command
     * @return the created {@link Mascota} entity
     */
    Mascota handle(CreateMascotaCommand command);

    /**
     * Handle update mascota command
     * @param command the {@link UpdateMascotaCommand} command
     * @return an {@link Optional} of {@link Mascota} entity
     */
    Optional<Mascota> handle(UpdateMascotaCommand command);

    /**
     * Handle delete mascota command
     * @param command the {@link DeleteMascotaCommand} command
     */
    void handle(DeleteMascotaCommand command);
}

