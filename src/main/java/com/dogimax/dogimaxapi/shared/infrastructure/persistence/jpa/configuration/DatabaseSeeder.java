package com.dogimax.dogimaxapi.shared.infrastructure.persistence.jpa.configuration;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.veterinary;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.AppointmentRepository;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.veterinaryRepository;
import com.dogimax.dogimaxapi.iam.domain.model.aggregates.User;
import com.dogimax.dogimaxapi.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import com.dogimax.dogimaxapi.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.dogimax.dogimaxapi.notification.domain.model.aggregates.Notification;
import com.dogimax.dogimaxapi.notification.infrastructure.persistence.jpa.repositories.NotificationJPARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Database seeder configuration
 * This class is responsible for seeding the database with initial data
 */
@Configuration
public class DatabaseSeeder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSeeder.class);

    /**
     * Seed the database with initial users, veterinarys and appointments
     * @param userRepository The user repository
     * @param hashingService The hashing service
     * @param veterinaryRepository The veterinary repository
     * @param appointmentRepository The appointment repository
     * @return CommandLineRunner
     */
    @Bean
    CommandLineRunner seedDatabase(UserRepository userRepository, 
                                   BCryptHashingService hashingService,
                                   veterinaryRepository veterinaryRepository,
                                   AppointmentRepository appointmentRepository,
                                   NotificationJPARepository notificationRepository) {
        return args -> {
            // Check if database is already seeded
            if (userRepository.count() > 0 || veterinaryRepository.count() > 0 || appointmentRepository.count() > 0) {
                LOGGER.info("Database already seeded. Skipping seeding process.");
                return;
            }

            LOGGER.info("Starting database seeding process...");

            // Create Pet Lover user
            var petLover = new User();
            petLover.setNombre("Carlos");
            petLover.setApellido("Gomez");
            petLover.setEmail("petlover@dogimax.com");
            petLover.setTelefono("+51987654321");
            petLover.setFechaRegistro(LocalDateTime.now());
            petLover.setPassword(hashingService.encode("petlover"));
            petLover.setRol("petlover");

            userRepository.save(petLover);
            LOGGER.info("Created Pet Lover user: {} {}", petLover.getNombre(), petLover.getApellido());

            // Create Veterinary user
            var veterinary = new User();
            veterinary.setNombre("Ana");
            veterinary.setApellido("Martinez");
            veterinary.setEmail("veterinary@dogimax.com");
            veterinary.setTelefono("+51912345678");
            veterinary.setFechaRegistro(LocalDateTime.now());
            veterinary.setPassword(hashingService.encode("veterinary"));
            veterinary.setRol("veterinary");

            userRepository.save(veterinary);
            LOGGER.info("Created Veterinary user: {} {}", veterinary.getNombre(), veterinary.getApellido());

            // Create veterinary 1
            var veterinary1 = new veterinary();
            veterinary1.setNombre("Clínica Veterinaria VetSalud");
            veterinary1.setDireccion("Av. Principal 123, Miraflores, Lima");
            veterinary1.setTelefono("+5114445566");
            veterinary1.setServicios(Arrays.asList("Consultas", "Vacunación", "Cirugía", "Laboratorio"));
            veterinary1.setHorario("L-V 9:00-20:00, S 9:00-17:00");

            veterinary1 = veterinaryRepository.save(veterinary1);
            LOGGER.info("Created veterinary: {} with ID: {}", veterinary1.getNombre(), veterinary1.getId());

            // Create veterinary 2
            var veterinary2 = new veterinary();
            veterinary2.setNombre("Centro Veterinario Animalia");
            veterinary2.setDireccion("Calle Los Pinos 456, San Isidro, Lima");
            veterinary2.setTelefono("+5112223344");
            veterinary2.setServicios(Arrays.asList("Consultas", "Emergencias 24h", "Ecografías", "Peluquería"));
            veterinary2.setHorario("24 horas");

            veterinary2 = veterinaryRepository.save(veterinary2);
            LOGGER.info("Created veterinary: {} with ID: {}", veterinary2.getNombre(), veterinary2.getId());

            // Create veterinary 3
            var veterinary3 = new veterinary();
            veterinary3.setNombre("Hospital Veterinario PetCare");
            veterinary3.setDireccion("Jr. Las Flores 789, Surco, Lima");
            veterinary3.setTelefono("+5115556677");
            veterinary3.setServicios(Arrays.asList("Consultas", "Hospitalización", "Rayos X", "Análisis clínicos"));
            veterinary3.setHorario("L-D 8:00-22:00");

            veterinary3 = veterinaryRepository.save(veterinary3);
            LOGGER.info("Created veterinary: {} with ID: {}", veterinary3.getNombre(), veterinary3.getId());

            // Create appointments for petLover user
            // Note: Using mascotaId = 1 as example (you'll need to have pets created separately)
            
            // Appointment 1 - Scheduled for next week
            var appointment1 = new Appointment(
                    1L, // mascotaId
                    veterinary1.getId(), // veterinaryId
                    LocalDateTime.now().plusDays(7).withHour(10).withMinute(0), // Next week at 10:00
                    "Vacunación anual",
                    "Programada",
                    "Traer cartilla de vacunación"
            );
            appointmentRepository.save(appointment1);
            LOGGER.info("Created appointment 1: {} at {}", appointment1.getMotivo(), appointment1.getFechaHora());

            // Appointment 2 - Scheduled for tomorrow
            var appointment2 = new Appointment(
                    1L, // mascotaId
                    veterinary2.getId(), // veterinaryId
                    LocalDateTime.now().plusDays(1).withHour(15).withMinute(30), // Tomorrow at 15:30
                    "Control de rutina",
                    "Programada",
                    "Primera visita al veterinario"
            );
            appointmentRepository.save(appointment2);
            LOGGER.info("Created appointment 2: {} at {}", appointment2.getMotivo(), appointment2.getFechaHora());

            // Appointment 3 - Past appointment (completed)
            var appointment3 = new Appointment(
                    1L, // mascotaId
                    veterinary3.getId(), // veterinaryId
                    LocalDateTime.now().minusDays(15).withHour(11).withMinute(0), // 15 days ago at 11:00
                    "Desparasitación",
                    "Completada",
                    "Se aplicó desparasitante interno y externo"
            );
            appointmentRepository.save(appointment3);
            LOGGER.info("Created appointment 3: {} at {}", appointment3.getMotivo(), appointment3.getFechaHora());

            // Appointment 4 - Scheduled for next month
            var appointment4 = new Appointment(
                    1L, // mascotaId
                    veterinary1.getId(), // veterinaryId
                    LocalDateTime.now().plusDays(30).withHour(9).withMinute(0), // Next month at 9:00
                    "Chequeo dental",
                    "Programada",
                    "Evaluación de higiene bucal"
            );
            appointmentRepository.save(appointment4);
            LOGGER.info("Created appointment 4: {} at {}", appointment4.getMotivo(), appointment4.getFechaHora());

            // Appointment 5 - Recent past appointment
            var appointment5 = new Appointment(
                    1L, // mascotaId
                    veterinary2.getId(), // veterinaryId
                    LocalDateTime.now().minusDays(3).withHour(16).withMinute(0), // 3 days ago at 16:00
                    "Análisis de sangre",
                    "Completada",
                    "Resultados normales, todo en orden"
            );
            appointmentRepository.save(appointment5);
            LOGGER.info("Created appointment 5: {} at {}", appointment5.getMotivo(), appointment5.getFechaHora());

            // Create notifications
            // Notification 1 - Welcome notification
            var notification1 = new Notification(
                    petLover.getId(),
                    "¡Bienvenido a Dogimax! Estamos encantados de tenerte con nosotros.",
                    "Bienvenida"
            );
            notificationRepository.save(notification1);
            LOGGER.info("Created notification 1: Welcome notification for user {}", petLover.getId());

            // Notification 2 - Appointment reminder (unread)
            var notification2 = new Notification(
                    petLover.getId(),
                    "Recordatorio: Tienes una cita programada para mañana a las 15:30 en Centro Veterinario Animalia.",
                    "RecordatorioCita"
            );
            notificationRepository.save(notification2);
            LOGGER.info("Created notification 2: Appointment reminder for user {}", petLover.getId());

            // Notification 3 - Vaccination reminder (unread)
            var notification3 = new Notification(
                    petLover.getId(),
                    "Es momento de la vacunación anual de tu mascota. Agenda tu cita en la sección de Citas.",
                    "RecordatorioSalud"
            );
            notificationRepository.save(notification3);
            LOGGER.info("Created notification 3: Vaccination reminder for user {}", petLover.getId());

            // Notification 4 - Exam results (read)
            var notification4 = new Notification(
                    petLover.getId(),
                    "Los resultados del análisis de sangre están disponibles. Todo está en orden.",
                    "ResultadoExamen"
            );
            notification4.markAsRead();
            notificationRepository.save(notification4);
            LOGGER.info("Created notification 4: Exam results for user {}", petLover.getId());

            // Notification 5 - Special offer (unread)
            var notification5 = new Notification(
                    petLover.getId(),
                    "¡Promoción especial! 20% de descuento en consultas preventivas durante este mes.",
                    "Promocion"
            );
            notificationRepository.save(notification5);
            LOGGER.info("Created notification 5: Special offer for user {}", petLover.getId());

            // Notification 6 - Appointment confirmation (read)
            var notification6 = new Notification(
                    petLover.getId(),
                    "Tu cita ha sido confirmada para el 7 de diciembre a las 10:00 en Clínica Veterinaria VetSalud.",
                    "Confirmacion"
            );
            notification6.markAsRead();
            notificationRepository.save(notification6);
            LOGGER.info("Created notification 6: Appointment confirmation for user {}", petLover.getId());

            // Notification 7 - Payment reminder (unread)
            var notification7 = new Notification(
                    petLover.getId(),
                    "Tienes un pago pendiente de S/150 por la consulta del 23 de noviembre.",
                    "RecordatorioPago"
            );
            notificationRepository.save(notification7);
            LOGGER.info("Created notification 7: Payment reminder for user {}", petLover.getId());

            // Notification 8 - Survey request (unread)
            var notification8 = new Notification(
                    petLover.getId(),
                    "Ayúdanos a mejorar. Completa nuestra encuesta de satisfacción sobre tu última visita.",
                    "Encuesta"
            );
            notificationRepository.save(notification8);
            LOGGER.info("Created notification 8: Survey request for user {}", petLover.getId());

            LOGGER.info("Database seeding completed successfully!");
        };
    }
}
