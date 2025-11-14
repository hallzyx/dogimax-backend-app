package com.dogimax.dogimaxapi.gestion_de_mascotas.infrastructure.persistence.jpa.repositories;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface is responsible for providing the Mascota entity related operations.
 * It extends the JpaRepository interface.
 */
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    /**
     * This method is responsible for finding all mascotas by user id.
     * @param userId The user id.
     * @return The list of mascotas.
     */
    List<Mascota> findByUserId(Long userId);

    /**
     * This method is responsible for finding all active mascotas by user id.
     * @param userId The user id.
     * @param activa The active status.
     * @return The list of mascotas.
     */
    List<Mascota> findByUserIdAndActiva(Long userId, Boolean activa);

    /**
     * This method is responsible for finding a mascota by id and user id.
     * @param id The mascota id.
     * @param userId The user id.
     * @return The mascota object.
     */
    Optional<Mascota> findByIdAndUserId(Long id, Long userId);
}

