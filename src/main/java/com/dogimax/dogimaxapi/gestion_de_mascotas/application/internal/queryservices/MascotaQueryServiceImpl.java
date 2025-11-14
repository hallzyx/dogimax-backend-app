package com.dogimax.dogimaxapi.gestion_de_mascotas.application.internal.queryservices;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates.Mascota;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.queries.GetAllMascotasQuery;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.queries.GetMascotaByIdQuery;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.queries.GetMascotasByUserIdQuery;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.services.MascotaQueryService;
import com.dogimax.dogimaxapi.gestion_de_mascotas.infrastructure.persistence.jpa.repositories.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Mascota query service implementation
 * <p>
 *     This class implements the {@link MascotaQueryService} interface and provides the implementation for the
 *     mascota queries.
 * </p>
 */
@Service
public class MascotaQueryServiceImpl implements MascotaQueryService {

    private final MascotaRepository mascotaRepository;

    public MascotaQueryServiceImpl(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    @Override
    public List<Mascota> handle(GetAllMascotasQuery query) {
        return mascotaRepository.findAll();
    }

    @Override
    public Optional<Mascota> handle(GetMascotaByIdQuery query) {
        return mascotaRepository.findById(query.id());
    }

    @Override
    public List<Mascota> handle(GetMascotasByUserIdQuery query) {
        return mascotaRepository.findByUserId(query.userId());
    }
}

