package com.dogimax.dogimaxapi.appointments.application.internal.queryservices;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.veterinary;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAllveterinarysQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetveterinaryByIdQuery;
import com.dogimax.dogimaxapi.appointments.domain.services.veterinaryQueryService;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.veterinaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * veterinary query service implementation
 */
@Service
public class veterinaryQueryServiceImpl implements veterinaryQueryService {

    private final veterinaryRepository veterinaryRepository;

    public veterinaryQueryServiceImpl(veterinaryRepository veterinaryRepository) {
        this.veterinaryRepository = veterinaryRepository;
    }

    @Override
    public List<veterinary> handle(GetAllveterinarysQuery query) {
        return veterinaryRepository.findAll();
    }

    @Override
    public Optional<veterinary> handle(GetveterinaryByIdQuery query) {
        return veterinaryRepository.findById(query.veterinaryId());
    }
}
