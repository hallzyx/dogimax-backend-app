package com.dogimax.dogimaxapi.gestion_de_mascotas.domain.services;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates.Mascota;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.queries.GetAllMascotasQuery;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.queries.GetMascotaByIdQuery;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.queries.GetMascotasByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Mascota query service
 * <p>
 *     This interface represents the service to handle mascota queries.
 * </p>
 */
public interface MascotaQueryService {
    /**
     * Handle get all mascotas query
     * @param query the {@link GetAllMascotasQuery} query
     * @return a list of {@link Mascota} entities
     */
    List<Mascota> handle(GetAllMascotasQuery query);

    /**
     * Handle get mascota by id query
     * @param query the {@link GetMascotaByIdQuery} query
     * @return an {@link Optional} of {@link Mascota} entity
     */
    Optional<Mascota> handle(GetMascotaByIdQuery query);

    /**
     * Handle get mascotas by user id query
     * @param query the {@link GetMascotasByUserIdQuery} query
     * @return a list of {@link Mascota} entities
     */
    List<Mascota> handle(GetMascotasByUserIdQuery query);
}

