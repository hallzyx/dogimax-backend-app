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
import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.MedicalHistory;
import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;
import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Recommendation;
import com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories.MedicalHistoryRepository;
import com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories.PetRepository;
import com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories.RecommendationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
                                   NotificationJPARepository notificationRepository,
                                   PetRepository petRepository,
                                   MedicalHistoryRepository medicalHistoryRepository,
                                   RecommendationRepository recommendationRepository) {
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

            // Create pets for petLover user
            // Pet 1 - Max (Dog)
            var pet1 = new Pet(
                    "Max",
                    "Perro",
                    "Golden Retriever",
                    LocalDate.of(2020, 3, 15),
                    Pet.Gender.MALE,
                    28.5,
                    "Dorado",
                    true,
                    "Muy activo y juguetón. Le encanta nadar.",
                    "https://images.unsplash.com/photo-1633722715463-d30f4f325e24",
                    petLover.getId()
            );
            petRepository.save(pet1);
            LOGGER.info("Created pet 1: {} ({})", pet1.getName(), pet1.getSpecies());

            // Pet 2 - Luna (Cat)
            var pet2 = new Pet(
                    "Luna",
                    "Gato",
                    "Siamés",
                    LocalDate.of(2021, 7, 20),
                    Pet.Gender.FEMALE,
                    4.2,
                    "Crema con puntas oscuras",
                    true,
                    "Tranquila y cariñosa. Le gusta dormir en lugares altos.",
                    "https://images.unsplash.com/photo-1513360371669-4adf3dd7dff8",
                    petLover.getId()
            );
            petRepository.save(pet2);
            LOGGER.info("Created pet 2: {} ({})", pet2.getName(), pet2.getSpecies());

            // Pet 3 - Rocky (Dog)
            var pet3 = new Pet(
                    "Rocky",
                    "Perro",
                    "Bulldog Francés",
                    LocalDate.of(2019, 11, 5),
                    Pet.Gender.MALE,
                    12.8,
                    "Blanco con manchas negras",
                    false,
                    "Sociable y protector. Necesita control de peso.",
                    "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e",
                    petLover.getId()
            );
            petRepository.save(pet3);
            LOGGER.info("Created pet 3: {} ({})", pet3.getName(), pet3.getSpecies());

            // Pet 4 - Mia (Cat)
            var pet4 = new Pet(
                    "Mia",
                    "Gato",
                    "Persa",
                    LocalDate.of(2022, 1, 10),
                    Pet.Gender.FEMALE,
                    5.1,
                    "Blanco",
                    true,
                    "Requiere cepillado diario por su pelaje largo.",
                    "https://images.unsplash.com/photo-1495360010541-f48722b34f7d",
                    petLover.getId()
            );
            petRepository.save(pet4);
            LOGGER.info("Created pet 4: {} ({})", pet4.getName(), pet4.getSpecies());

            // Pet 5 - Bruno (Dog)
            var pet5 = new Pet(
                    "Bruno",
                    "Perro",
                    "Labrador",
                    LocalDate.of(2018, 5, 25),
                    Pet.Gender.MALE,
                    32.0,
                    "Negro",
                    true,
                    "Tiene displasia de cadera leve. Tratamiento en curso.",
                    "https://images.unsplash.com/photo-1517849845537-4d257902454a",
                    petLover.getId()
            );
            petRepository.save(pet5);
            LOGGER.info("Created pet 5: {} ({})", pet5.getName(), pet5.getSpecies());

            // Pet 6 - Coco (Rabbit)
            var pet6 = new Pet(
                    "Coco",
                    "Conejo",
                    "Mini Lop",
                    LocalDate.of(2023, 2, 14),
                    Pet.Gender.FEMALE,
                    1.8,
                    "Gris con blanco",
                    true,
                    "Muy asustadiza. Necesita ambiente tranquilo.",
                    "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308",
                    petLover.getId()
            );
            petRepository.save(pet6);
            LOGGER.info("Created pet 6: {} ({})", pet6.getName(), pet6.getSpecies());

            LOGGER.info("Created {} pets for user {}", 6, petLover.getId());

            // Create medical histories for pets
            // Medical History 1 - Vaccination for Max
            var medicalHistory1 = new MedicalHistory(
                    pet1.getId(),
                    LocalDateTime.now().minusMonths(6),
                    MedicalHistory.RecordType.VACCINATION,
                    "Vacuna antirrábica y refuerzo múltiple",
                    "Dr. Roberto Sánchez",
                    "Mascota en excelente estado de salud. Sin reacciones adversas.",
                    null,
                    80.0,
                    LocalDateTime.now().plusMonths(6)
            );
            medicalHistoryRepository.save(medicalHistory1);
            LOGGER.info("Created medical history 1 for pet {}", pet1.getName());

            // Medical History 2 - Consultation for Luna
            var medicalHistory2 = new MedicalHistory(
                    pet2.getId(),
                    LocalDateTime.now().minusMonths(2),
                    MedicalHistory.RecordType.CONSULTATION,
                    "Control de peso y revisión dental",
                    "Dra. María Torres",
                    "Se recomienda dieta baja en calorías y limpieza dental.",
                    null,
                    120.0,
                    LocalDateTime.now().plusMonths(3)
            );
            medicalHistoryRepository.save(medicalHistory2);
            LOGGER.info("Created medical history 2 for pet {}", pet2.getName());

            // Medical History 3 - Surgery for Rocky
            var medicalHistory3 = new MedicalHistory(
                    pet3.getId(),
                    LocalDateTime.now().minusMonths(4),
                    MedicalHistory.RecordType.SURGERY,
                    "Cirugía de esterilización",
                    "Dr. Carlos Mendoza",
                    "Cirugía exitosa. Recuperación post-operatoria sin complicaciones.",
                    "surgery_report_rocky.pdf",
                    450.0,
                    LocalDateTime.now().minusMonths(3).plusWeeks(2)
            );
            medicalHistoryRepository.save(medicalHistory3);
            LOGGER.info("Created medical history 3 for pet {}", pet3.getName());

            // Medical History 4 - Exam for Bruno
            var medicalHistory4 = new MedicalHistory(
                    pet5.getId(),
                    LocalDateTime.now().minusMonths(1),
                    MedicalHistory.RecordType.EXAM,
                    "Radiografía de cadera para evaluación de displasia",
                    "Dr. Roberto Sánchez",
                    "Displasia de cadera leve detectada. Se recomienda tratamiento con suplementos.",
                    "xray_bruno_hip.jpg",
                    250.0,
                    LocalDateTime.now().plusMonths(3)
            );
            medicalHistoryRepository.save(medicalHistory4);
            LOGGER.info("Created medical history 4 for pet {}", pet5.getName());

            // Medical History 5 - Treatment for Mia
            var medicalHistory5 = new MedicalHistory(
                    pet4.getId(),
                    LocalDateTime.now().minusWeeks(2),
                    MedicalHistory.RecordType.TREATMENT,
                    "Tratamiento para dermatitis por alergia",
                    "Dra. María Torres",
                    "Aplicación de shampoo medicado y corticoides. Mejoría notable.",
                    null,
                    150.0,
                    LocalDateTime.now().plusWeeks(4)
            );
            medicalHistoryRepository.save(medicalHistory5);
            LOGGER.info("Created medical history 5 for pet {}", pet4.getName());

            // Medical History 6 - Additional vaccination for Max
            var medicalHistory6 = new MedicalHistory(
                    pet1.getId(),
                    LocalDateTime.now().minusYears(1),
                    MedicalHistory.RecordType.VACCINATION,
                    "Vacuna contra parvovirus y moquillo",
                    "Dr. Roberto Sánchez",
                    "Primera dosis de vacunación. Mascota respondió bien.",
                    null,
                    75.0,
                    LocalDateTime.now().minusYears(1).plusWeeks(3)
            );
            medicalHistoryRepository.save(medicalHistory6);
            LOGGER.info("Created medical history 6 for pet {}", pet1.getName());

            // Medical History 7 - Consultation for Luna (dental)
            var medicalHistory7 = new MedicalHistory(
                    pet2.getId(),
                    LocalDateTime.now().minusMonths(8),
                    MedicalHistory.RecordType.CONSULTATION,
                    "Consulta por mal aliento y sarro dental",
                    "Dra. María Torres",
                    "Se detectó sarro moderado. Programada limpieza dental.",
                    null,
                    100.0,
                    LocalDateTime.now().minusMonths(7)
            );
            medicalHistoryRepository.save(medicalHistory7);
            LOGGER.info("Created medical history 7 for pet {}", pet2.getName());

            // Medical History 8 - Exam for Rocky (skin)
            var medicalHistory8 = new MedicalHistory(
                    pet3.getId(),
                    LocalDateTime.now().minusMonths(9),
                    MedicalHistory.RecordType.EXAM,
                    "Análisis dermatológico por prurito",
                    "Dr. Carlos Mendoza",
                    "Alergia alimentaria detectada. Se cambió la dieta.",
                    "skin_test_rocky.pdf",
                    180.0,
                    LocalDateTime.now().minusMonths(8)
            );
            medicalHistoryRepository.save(medicalHistory8);
            LOGGER.info("Created medical history 8 for pet {}", pet3.getName());

            // Medical History 9 - Treatment for Mia (eyes)
            var medicalHistory9 = new MedicalHistory(
                    pet4.getId(),
                    LocalDateTime.now().minusMonths(5),
                    MedicalHistory.RecordType.TREATMENT,
                    "Tratamiento para conjuntivitis",
                    "Dra. María Torres",
                    "Aplicación de gotas oftálmicas durante 7 días. Recuperación completa.",
                    null,
                    90.0,
                    null
            );
            medicalHistoryRepository.save(medicalHistory9);
            LOGGER.info("Created medical history 9 for pet {}", pet4.getName());

            // Medical History 10 - Surgery for Bruno (dental)
            var medicalHistory10 = new MedicalHistory(
                    pet5.getId(),
                    LocalDateTime.now().minusYears(2),
                    MedicalHistory.RecordType.SURGERY,
                    "Extracción de muela dañada",
                    "Dr. Roberto Sánchez",
                    "Cirugía exitosa. Antibiótico post-operatorio prescrito.",
                    "dental_surgery_bruno.pdf",
                    380.0,
                    LocalDateTime.now().minusYears(2).plusWeeks(2)
            );
            medicalHistoryRepository.save(medicalHistory10);
            LOGGER.info("Created medical history 10 for pet {}", pet5.getName());

            // Medical History 11 - Consultation for Coco
            var medicalHistory11 = new MedicalHistory(
                    pet6.getId(),
                    LocalDateTime.now().minusMonths(3),
                    MedicalHistory.RecordType.CONSULTATION,
                    "Primera consulta - Chequeo general",
                    "Dra. María Torres",
                    "Conejo en buen estado de salud. Se recomendó vacunación y desparasitación.",
                    null,
                    110.0,
                    LocalDateTime.now().plusMonths(3)
            );
            medicalHistoryRepository.save(medicalHistory11);
            LOGGER.info("Created medical history 11 for pet {}", pet6.getName());

            // Medical History 12 - Vaccination for Luna
            var medicalHistory12 = new MedicalHistory(
                    pet2.getId(),
                    LocalDateTime.now().minusYears(1).minusMonths(1),
                    MedicalHistory.RecordType.VACCINATION,
                    "Vacuna triple felina (Panleucopenia, Rinotraqueítis, Calicivirus)",
                    "Dr. Roberto Sánchez",
                    "Primera dosis aplicada. Refuerzo programado en 3 semanas.",
                    null,
                    85.0,
                    LocalDateTime.now().minusYears(1).plusWeeks(3)
            );
            medicalHistoryRepository.save(medicalHistory12);
            LOGGER.info("Created medical history 12 for pet {}", pet2.getName());

            // Medical History 13 - Exam for Rocky (blood test)
            var medicalHistory13 = new MedicalHistory(
                    pet3.getId(),
                    LocalDateTime.now().minusMonths(6),
                    MedicalHistory.RecordType.EXAM,
                    "Análisis de sangre completo pre-quirúrgico",
                    "Dr. Carlos Mendoza",
                    "Valores normales. Mascota apta para cirugía.",
                    "bloodtest_rocky.pdf",
                    200.0,
                    null
            );
            medicalHistoryRepository.save(medicalHistory13);
            LOGGER.info("Created medical history 13 for pet {}", pet3.getName());

            // Medical History 14 - Treatment for Max (ear infection)
            var medicalHistory14 = new MedicalHistory(
                    pet1.getId(),
                    LocalDateTime.now().minusWeeks(8),
                    MedicalHistory.RecordType.TREATMENT,
                    "Tratamiento para otitis externa",
                    "Dra. María Torres",
                    "Infección en oído derecho. Gotas óticas y antibiótico oral por 10 días.",
                    null,
                    135.0,
                    LocalDateTime.now().minusWeeks(6)
            );
            medicalHistoryRepository.save(medicalHistory14);
            LOGGER.info("Created medical history 14 for pet {}", pet1.getName());

            // Medical History 15 - Consultation for Bruno (follow-up)
            var medicalHistory15 = new MedicalHistory(
                    pet5.getId(),
                    LocalDateTime.now().minusWeeks(3),
                    MedicalHistory.RecordType.CONSULTATION,
                    "Control de displasia - Evaluación de movilidad",
                    "Dr. Roberto Sánchez",
                    "Ligera mejoría con el tratamiento. Continuar con suplementos.",
                    null,
                    95.0,
                    LocalDateTime.now().plusMonths(2)
            );
            medicalHistoryRepository.save(medicalHistory15);
            LOGGER.info("Created medical history 15 for pet {}", pet5.getName());

            LOGGER.info("Created {} medical histories", 15);

            // Create recommendations for pets
            // Recommendation 1 - Nutrition for Max
            var recommendation1 = new Recommendation(
                    pet1.getId(),
                    Recommendation.RecommendationType.NUTRITION,
                    "Ajustar porción de alimento diario",
                    "Max ha ganado peso recientemente. Se recomienda reducir la porción diaria en un 10% y aumentar el ejercicio físico.",
                    Recommendation.Priority.MEDIUM,
                    LocalDateTime.now().plusMonths(1),
                    "VetAI-Nutrition-v2",
                    0.87,
                    "{\"current_weight\": 28.5, \"ideal_weight\": 27.0, \"activity_level\": \"high\"}"
            );
            recommendationRepository.save(recommendation1);
            LOGGER.info("Created recommendation 1 for pet {}", pet1.getName());

            // Recommendation 2 - Exercise for Rocky
            var recommendation2 = new Recommendation(
                    pet3.getId(),
                    Recommendation.RecommendationType.EXERCISE,
                    "Incrementar actividad física gradualmente",
                    "Para prevenir el sobrepeso, se recomienda aumentar las caminatas a 30 minutos dos veces al día.",
                    Recommendation.Priority.HIGH,
                    LocalDateTime.now().plusWeeks(2),
                    "VetAI-Exercise-v1",
                    0.92,
                    "{\"current_exercise\": 15, \"target_exercise\": 30, \"frequency\": \"daily\"}"
            );
            recommendationRepository.save(recommendation2);
            LOGGER.info("Created recommendation 2 for pet {}", pet3.getName());

            // Recommendation 3 - Health for Bruno
            var recommendation3 = new Recommendation(
                    pet5.getId(),
                    Recommendation.RecommendationType.HEALTH,
                    "Suplementos para displasia de cadera",
                    "Iniciar tratamiento con glucosamina y condroitina para mejorar la movilidad articular.",
                    Recommendation.Priority.CRITICAL,
                    LocalDateTime.now().plusDays(7),
                    "VetAI-Health-v3",
                    0.95,
                    "{\"condition\": \"hip_dysplasia\", \"severity\": \"mild\", \"supplements\": [\"glucosamine\", \"chondroitin\"]}"
            );
            recommendationRepository.save(recommendation3);
            LOGGER.info("Created recommendation 3 for pet {}", pet5.getName());

            // Recommendation 4 - Vaccination for Luna
            var recommendation4 = new Recommendation(
                    pet2.getId(),
                    Recommendation.RecommendationType.VACCINATION,
                    "Vacuna triple felina próxima",
                    "Agendar cita para vacuna triple felina. La última dosis fue hace 11 meses.",
                    Recommendation.Priority.HIGH,
                    LocalDateTime.now().plusWeeks(1),
                    "VetAI-Vaccination-v1",
                    0.99,
                    "{\"vaccine_type\": \"triple_felina\", \"last_dose\": \"11_months_ago\"}"
            );
            recommendationRepository.save(recommendation4);
            LOGGER.info("Created recommendation 4 for pet {}", pet2.getName());

            // Recommendation 5 - Care for Mia (Completed)
            var recommendation5 = new Recommendation(
                    pet4.getId(),
                    Recommendation.RecommendationType.CARE,
                    "Cepillado diario del pelaje",
                    "Debido a su pelaje largo, Mia requiere cepillado diario para evitar nudos y problemas de piel.",
                    Recommendation.Priority.MEDIUM,
                    null,
                    "VetAI-Care-v2",
                    0.88,
                    "{\"grooming_frequency\": \"daily\", \"tool\": \"slicker_brush\"}"
            );
            recommendation5.markAsCompleted();
            recommendationRepository.save(recommendation5);
            LOGGER.info("Created recommendation 5 for pet {} (completed)", pet4.getName());

            // Recommendation 6 - Behavior for Coco
            var recommendation6 = new Recommendation(
                    pet6.getId(),
                    Recommendation.RecommendationType.BEHAVIOR,
                    "Socialización gradual",
                    "Coco es muy asustadiza. Se recomienda exponerla gradualmente a nuevos ambientes y personas de forma controlada.",
                    Recommendation.Priority.LOW,
                    LocalDateTime.now().plusMonths(2),
                    "VetAI-Behavior-v1",
                    0.82,
                    "{\"issue\": \"fearfulness\", \"approach\": \"gradual_exposure\"}"
            );
            recommendationRepository.save(recommendation6);
            LOGGER.info("Created recommendation 6 for pet {}", pet6.getName());

            LOGGER.info("Created {} recommendations", 6);

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
