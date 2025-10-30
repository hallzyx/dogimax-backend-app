package com.dogimax.dogimaxapi.appointments.domain.services;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.veterinary;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetAllveterinarysQuery;
import com.dogimax.dogimaxapi.appointments.domain.model.queries.GetveterinaryByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * veterinary query service interface
 */
public interface veterinaryQueryService {
    List<veterinary> handle(GetAllveterinarysQuery query);
    Optional<veterinary> handle(GetveterinaryByIdQuery query);
}
