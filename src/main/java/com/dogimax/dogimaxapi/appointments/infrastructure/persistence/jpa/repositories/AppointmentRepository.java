package com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Appointment repository
 * This interface is used to manage appointments in the database
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    /**
     * Find appointments by mascota id
     * @param mascotaId The mascota id
     * @return List of appointments
     */
    List<Appointment> findByMascotaId(Long mascotaId);

    /**
     * Find appointments by veterinary id
     * @param veterinaryId The veterinary id
     * @return List of appointments
     */
    List<Appointment> findByVeterinaryId(Long veterinaryId);
}
