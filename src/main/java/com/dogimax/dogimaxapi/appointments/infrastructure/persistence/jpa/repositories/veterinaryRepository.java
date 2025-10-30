package com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.veterinary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * veterinary repository
 */
@Repository
public interface veterinaryRepository extends JpaRepository<veterinary, Long> {
}
