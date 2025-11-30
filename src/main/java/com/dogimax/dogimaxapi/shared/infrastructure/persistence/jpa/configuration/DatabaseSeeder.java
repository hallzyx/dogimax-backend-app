package com.dogimax.dogimaxapi.shared.infrastructure.persistence.jpa.configuration;

import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.Appointment;
import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.veterinary;
import com.dogimax.dogimaxapi.appointments.domain.model.aggregates.VeterinaryStaff;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.AppointmentRepository;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.veterinaryRepository;
import com.dogimax.dogimaxapi.appointments.infrastructure.persistence.jpa.repositories.VeterinaryStaffRepository;
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
                                   RecommendationRepository recommendationRepository,
                                   VeterinaryStaffRepository veterinaryStaffRepository) {
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

            // Create VeterinaryStaff relationships (junction table)
            // Link veterinary user (Ana Martinez) to all three clinics
            var vetStaff1 = new VeterinaryStaff(
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    veterinary1.getId(), // VetSalud clinic
                    "Medicina General y Cirugía",
                    true
            );
            veterinaryStaffRepository.save(vetStaff1);
            LOGGER.info("Created VeterinaryStaff: User {} works at Clinic {} with specialty: {}", 
                    veterinary.getId(), veterinary1.getId(), vetStaff1.getSpecialty());

            var vetStaff2 = new VeterinaryStaff(
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    veterinary2.getId(), // Animalia clinic
                    "Emergencias y Cuidados Intensivos",
                    true
            );
            veterinaryStaffRepository.save(vetStaff2);
            LOGGER.info("Created VeterinaryStaff: User {} works at Clinic {} with specialty: {}", 
                    veterinary.getId(), veterinary2.getId(), vetStaff2.getSpecialty());

            var vetStaff3 = new VeterinaryStaff(
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    veterinary3.getId(), // PetCare hospital
                    "Diagnóstico por Imagen",
                    true
            );
            veterinaryStaffRepository.save(vetStaff3);
            LOGGER.info("Created VeterinaryStaff: User {} works at Clinic {} with specialty: {}", 
                    veterinary.getId(), veterinary3.getId(), vetStaff3.getSpecialty());

            LOGGER.info("Created {} VeterinaryStaff relationships", 3);

            // Create appointments for petLover user
            // Note: Using mascotaId = 1 as example (you'll need to have pets created separately)
            
            // Appointment 1 - COMPLETED by veterinary user (Pet Max - for followup testing)
            var appointment1 = new Appointment(
                    pet1.getId(), // Max - Golden Retriever
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(15).withHour(10).withMinute(0),
                    "Vacunación antirrábica y chequeo general",
                    "En progreso",
                    "Vacuna aplicada correctamente. Mascota en excelente estado."
            );
            appointment1.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment1);
            LOGGER.info("Created appointment 1: {} - veterinaryStatus: {}", appointment1.getMotivo(), appointment1.getVeterinaryStatus());

            // Appointment 2 - COMPLETED by veterinary user (Pet Luna - for followup testing)
            var appointment2 = new Appointment(
                    pet2.getId(), // Luna - Siamés
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(20).withHour(15).withMinute(30),
                    "Control de peso y limpieza dental",
                    "En progreso",
                    "Se realizó limpieza dental. Peso estable."
            );
            appointment2.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment2);
            LOGGER.info("Created appointment 2: {} - veterinaryStatus: {}", appointment2.getMotivo(), appointment2.getVeterinaryStatus());

            // Appointment 3 - COMPLETED by veterinary user (Pet Max again - multiple visits)
            var appointment3 = new Appointment(
                    pet1.getId(), // Max - Golden Retriever (segunda cita)
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(45).withHour(11).withMinute(0),
                    "Tratamiento para otitis",
                    "En progreso",
                    "Tratamiento con gotas óticas. Mejora significativa."
            );
            appointment3.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment3);
            LOGGER.info("Created appointment 3: {} - veterinaryStatus: {}", appointment3.getMotivo(), appointment3.getVeterinaryStatus());

            // Appointment 4 - COMPLETED by veterinary user (Pet Rocky - for followup testing)
            var appointment4 = new Appointment(
                    pet3.getId(), // Rocky - Bulldog Francés
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(30).withHour(14).withMinute(0),
                    "Control post-cirugía de esterilización",
                    "En progreso",
                    "Recuperación excelente. Puntos retirados sin complicaciones."
            );
            appointment4.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment4);
            LOGGER.info("Created appointment 4: {} - veterinaryStatus: {}", appointment4.getMotivo(), appointment4.getVeterinaryStatus());

            // Appointment 5 - ACCEPTED by veterinary user (Pet Max - current accepted appointment)
            var appointment5 = new Appointment(
                    pet1.getId(), // Max - Golden Retriever
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().plusDays(2).withHour(10).withMinute(0),
                    "Chequeo de seguimiento post-vacunación",
                    "En progreso",
                    "Verificar reacción a la vacuna"
            );
            appointment5.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(appointment5);
            LOGGER.info("Created appointment 5: {} - veterinaryStatus: {}", appointment5.getMotivo(), appointment5.getVeterinaryStatus());

            // Appointment 6 - ACCEPTED by veterinary user (Pet Luna - current accepted appointment)
            var appointment6 = new Appointment(
                    pet2.getId(), // Luna - Siamés
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().plusDays(5).withHour(16).withMinute(30),
                    "Control de peso y revisión dental",
                    "En progreso",
                    "Seguimiento del tratamiento dental"
            );
            appointment6.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(appointment6);
            LOGGER.info("Created appointment 6: {} - veterinaryStatus: {}", appointment6.getMotivo(), appointment6.getVeterinaryStatus());

            // Appointment 7 - ACCEPTED by veterinary user (Pet Rocky - current accepted appointment)
            var appointment7 = new Appointment(
                    pet3.getId(), // Rocky - Bulldog Francés
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().plusDays(7).withHour(9).withMinute(0),
                    "Control nutricional",
                    "En progreso",
                    "Evaluar plan de control de peso"
            );
            appointment7.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(appointment7);
            LOGGER.info("Created appointment 7: {} - veterinaryStatus: {}", appointment7.getMotivo(), appointment7.getVeterinaryStatus());

            // Appointment 8 - PENDING (not yet accepted by veterinary)
            var appointment8 = new Appointment(
                    pet4.getId(), // Mia - Persa
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().plusDays(10).withHour(11).withMinute(30),
                    "Consulta por dermatitis",
                    "En progreso",
                    "Primera consulta"
            );
            appointment8.setVeterinaryStatus("PENDING");
            appointmentRepository.save(appointment8);
            LOGGER.info("Created appointment 8: {} - veterinaryStatus: {}", appointment8.getMotivo(), appointment8.getVeterinaryStatus());

            // Appointment 9 - PENDING (not yet accepted by veterinary)
            var appointment9 = new Appointment(
                    pet5.getId(), // Bruno - Labrador
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().plusDays(14).withHour(14).withMinute(0),
                    "Evaluación de displasia de cadera",
                    "En progreso",
                    "Revisar radiografías"
            );
            appointment9.setVeterinaryStatus("PENDING");
            appointmentRepository.save(appointment9);
            LOGGER.info("Created appointment 9: {} - veterinaryStatus: {}", appointment9.getMotivo(), appointment9.getVeterinaryStatus());

            // Appointment 10 - REJECTED by veterinary user
            var appointment10 = new Appointment(
                    pet6.getId(), // Coco - Conejo
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().plusDays(3).withHour(15).withMinute(0),
                    "Chequeo general",
                    "Cancelada",
                    "Reagendar para fecha posterior"
            );
            appointment10.setVeterinaryStatus("REJECTED");
            appointmentRepository.save(appointment10);
            LOGGER.info("Created appointment 10: {} - veterinaryStatus: {}", appointment10.getMotivo(), appointment10.getVeterinaryStatus());

            // Additional COMPLETED appointments by veterinary user for Patient History
            // More appointments with Max (Golden Retriever)
            var appointment11 = new Appointment(
                    pet1.getId(), // Max - Golden Retriever
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(60).withHour(9).withMinute(0),
                    "Control nutricional",
                    "En progreso",
                    "Ajuste de dieta por sobrepeso"
            );
            appointment11.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment11);
            LOGGER.info("Created appointment 11: {} - veterinaryStatus: {}", appointment11.getMotivo(), appointment11.getVeterinaryStatus());

            var appointment12 = new Appointment(
                    pet1.getId(), // Max - Golden Retriever
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(90).withHour(14).withMinute(30),
                    "Consulta dermatológica",
                    "En progreso",
                    "Tratamiento para dermatitis estacional"
            );
            appointment12.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment12);
            LOGGER.info("Created appointment 12: {} - veterinaryStatus: {}", appointment12.getMotivo(), appointment12.getVeterinaryStatus());

            var appointment13 = new Appointment(
                    pet1.getId(), // Max - Golden Retriever
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(120).withHour(11).withMinute(15),
                    "Vacunación múltiple",
                    "En progreso",
                    "Vacunas anuales aplicadas"
            );
            appointment13.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment13);
            LOGGER.info("Created appointment 13: {} - veterinaryStatus: {}", appointment13.getMotivo(), appointment13.getVeterinaryStatus());

            // More appointments with Luna (Siamés)
            var appointment14 = new Appointment(
                    pet2.getId(), // Luna - Siamés
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(35).withHour(16).withMinute(0),
                    "Control nutricional",
                    "En progreso",
                    "Peso ideal mantenido"
            );
            appointment14.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment14);
            LOGGER.info("Created appointment 14: {} - veterinaryStatus: {}", appointment14.getMotivo(), appointment14.getVeterinaryStatus());

            var appointment15 = new Appointment(
                    pet2.getId(), // Luna - Siamés
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(70).withHour(10).withMinute(30),
                    "Consulta dermatológica",
                    "En progreso",
                    "Alergia alimentaria identificada"
            );
            appointment15.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment15);
            LOGGER.info("Created appointment 15: {} - veterinaryStatus: {}", appointment15.getMotivo(), appointment15.getVeterinaryStatus());

            var appointment16 = new Appointment(
                    pet2.getId(), // Luna - Siamés
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(100).withHour(13).withMinute(45),
                    "Revisión dental",
                    "En progreso",
                    "Limpieza dental profunda realizada"
            );
            appointment16.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment16);
            LOGGER.info("Created appointment 16: {} - veterinaryStatus: {}", appointment16.getMotivo(), appointment16.getVeterinaryStatus());

            // More appointments with Rocky (Bulldog Francés)
            var appointment17 = new Appointment(
                    pet3.getId(), // Rocky - Bulldog Francés
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(50).withHour(15).withMinute(20),
                    "Vacunación múltiple",
                    "En progreso",
                    "Refuerzo anual aplicado"
            );
            appointment17.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment17);
            LOGGER.info("Created appointment 17: {} - veterinaryStatus: {}", appointment17.getMotivo(), appointment17.getVeterinaryStatus());

            var appointment18 = new Appointment(
                    pet3.getId(), // Rocky - Bulldog Francés
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(80).withHour(9).withMinute(45),
                    "Consulta general",
                    "En progreso",
                    "Chequeo de rutina - todo normal"
            );
            appointment18.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment18);
            LOGGER.info("Created appointment 18: {} - veterinaryStatus: {}", appointment18.getMotivo(), appointment18.getVeterinaryStatus());

            // More appointments with Mia (Persa)
            var appointment19 = new Appointment(
                    pet4.getId(), // Mia - Persa
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(40).withHour(14).withMinute(10),
                    "Control nutricional",
                    "En progreso",
                    "Dieta especial para pelo largo"
            );
            appointment19.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment19);
            LOGGER.info("Created appointment 19: {} - veterinaryStatus: {}", appointment19.getMotivo(), appointment19.getVeterinaryStatus());

            var appointment20 = new Appointment(
                    pet4.getId(), // Mia - Persa
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(85).withHour(11).withMinute(25),
                    "Vacunación múltiple",
                    "En progreso",
                    "Primera vacunación como adulto"
            );
            appointment20.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment20);
            LOGGER.info("Created appointment 20: {} - veterinaryStatus: {}", appointment20.getVeterinaryStatus(), appointment20.getVeterinaryStatus());

            // More appointments with Bruno (Labrador)
            var appointment21 = new Appointment(
                    pet5.getId(), // Bruno - Labrador
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(10).withHour(16).withMinute(40),
                    "Revisión dental",
                    "En progreso",
                    "Limpieza dental y extracción"
            );
            appointment21.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment21);
            LOGGER.info("Created appointment 21: {} - veterinaryStatus: {}", appointment21.getMotivo(), appointment21.getVeterinaryStatus());

            var appointment22 = new Appointment(
                    pet5.getId(), // Bruno - Labrador
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(55).withHour(8).withMinute(30),
                    "Consulta dermatológica",
                    "En progreso",
                    "Tratamiento para hot spots"
            );
            appointment22.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment22);
            LOGGER.info("Created appointment 22: {} - veterinaryStatus: {}", appointment22.getMotivo(), appointment22.getVeterinaryStatus());

            // More appointments with Coco (Conejo)
            var appointment23 = new Appointment(
                    pet6.getId(), // Coco - Conejo
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(65).withHour(12).withMinute(15),
                    "Control nutricional",
                    "En progreso",
                    "Dieta balanceada para conejos"
            );
            appointment23.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment23);
            LOGGER.info("Created appointment 23: {} - veterinaryStatus: {}", appointment23.getMotivo(), appointment23.getVeterinaryStatus());

            var appointment24 = new Appointment(
                    pet6.getId(), // Coco - Conejo
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.now().minusDays(95).withHour(17).withMinute(0),
                    "Consulta general",
                    "En progreso",
                    "Chequeo inicial - mascota sana"
            );
            appointment24.setVeterinaryStatus("COMPLETED");
            appointmentRepository.save(appointment24);
            LOGGER.info("Created appointment 24: {} - veterinaryStatus: {}", appointment24.getMotivo(), appointment24.getVeterinaryStatus());

            // DAILY AGENDA APPOINTMENTS - From TODAY (29/11/2025) to next week (06/12/2025)
            
            // TODAY - 29/11/2025
            var dailyApp1 = new Appointment(
                    pet1.getId(), // Max
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 11, 29, 9, 0),
                    "Consulta general",
                    "Aceptada",
                    "Cita para hoy"
            );
            dailyApp1.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp1);
            LOGGER.info("Created DAILY appointment 1: TODAY 09:00 - {}", dailyApp1.getMotivo());

            var dailyApp2 = new Appointment(
                    pet2.getId(), // Luna
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 11, 29, 10, 30),
                    "Control nutricional",
                    "Aceptada",
                    "Seguimiento de dieta"
            );
            dailyApp2.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp2);
            LOGGER.info("Created DAILY appointment 2: TODAY 10:30 - {}", dailyApp2.getMotivo());

            var dailyApp3 = new Appointment(
                    pet3.getId(), // Rocky
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 11, 29, 14, 0),
                    "Vacunación múltiple",
                    "Aceptada",
                    "Vacunas de refuerzo"
            );
            dailyApp3.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp3);
            LOGGER.info("Created DAILY appointment 3: TODAY 14:00 - {}", dailyApp3.getMotivo());

            var dailyApp4 = new Appointment(
                    pet4.getId(), // Mia
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 11, 29, 16, 15),
                    "Revisión dental",
                    "Aceptada",
                    "Limpieza dental programada"
            );
            dailyApp4.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp4);
            LOGGER.info("Created DAILY appointment 4: TODAY 16:15 - {}", dailyApp4.getMotivo());

            // SATURDAY - 30/11/2025
            var dailyApp5 = new Appointment(
                    pet5.getId(), // Bruno
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 11, 30, 8, 45),
                    "Consulta dermatológica",
                    "Aceptada",
                    "Control de dermatitis"
            );
            dailyApp5.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp5);
            LOGGER.info("Created DAILY appointment 5: SAT 30/11 08:45 - {}", dailyApp5.getMotivo());

            var dailyApp6 = new Appointment(
                    pet6.getId(), // Coco
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 11, 30, 11, 20),
                    "Consulta general",
                    "Aceptada",
                    "Chequeo de rutina"
            );
            dailyApp6.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp6);
            LOGGER.info("Created DAILY appointment 6: SAT 30/11 11:20 - {}", dailyApp6.getMotivo());

            var dailyApp7 = new Appointment(
                    pet1.getId(), // Max
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 11, 30, 15, 30),
                    "Control nutricional",
                    "Aceptada",
                    "Control post-dieta"
            );
            dailyApp7.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp7);
            LOGGER.info("Created DAILY appointment 7: SAT 30/11 15:30 - {}", dailyApp7.getMotivo());

            // SUNDAY - 01/12/2025
            var dailyApp8 = new Appointment(
                    pet2.getId(), // Luna
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 1, 9, 15),
                    "Vacunación múltiple",
                    "Aceptada",
                    "Refuerzo anual"
            );
            dailyApp8.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp8);
            LOGGER.info("Created DAILY appointment 8: SUN 01/12 09:15 - {}", dailyApp8.getMotivo());

            var dailyApp9 = new Appointment(
                    pet3.getId(), // Rocky
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 1, 13, 45),
                    "Consulta general",
                    "Aceptada",
                    "Control post-vacunación"
            );
            dailyApp9.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp9);
            LOGGER.info("Created DAILY appointment 9: SUN 01/12 13:45 - {}", dailyApp9.getMotivo());

            // MONDAY - 02/12/2025
            var dailyApp10 = new Appointment(
                    pet4.getId(), // Mia
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 2, 10, 0),
                    "Revisión dental",
                    "Aceptada",
                    "Seguimiento dental"
            );
            dailyApp10.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp10);
            LOGGER.info("Created DAILY appointment 10: MON 02/12 10:00 - {}", dailyApp10.getMotivo());

            var dailyApp11 = new Appointment(
                    pet5.getId(), // Bruno
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 2, 14, 20),
                    "Consulta dermatológica",
                    "Aceptada",
                    "Control de tratamiento"
            );
            dailyApp11.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp11);
            LOGGER.info("Created DAILY appointment 11: MON 02/12 14:20 - {}", dailyApp11.getMotivo());

            // TUESDAY - 03/12/2025
            var dailyApp12 = new Appointment(
                    pet6.getId(), // Coco
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 3, 8, 30),
                    "Control nutricional",
                    "Aceptada",
                    "Evaluación de peso"
            );
            dailyApp12.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp12);
            LOGGER.info("Created DAILY appointment 12: TUE 03/12 08:30 - {}", dailyApp12.getMotivo());

            var dailyApp13 = new Appointment(
                    pet1.getId(), // Max
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 3, 12, 0),
                    "Consulta general",
                    "Aceptada",
                    "Chequeo de rutina"
            );
            dailyApp13.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp13);
            LOGGER.info("Created DAILY appointment 13: TUE 03/12 12:00 - {}", dailyApp13.getMotivo());

            // WEDNESDAY - 04/12/2025
            var dailyApp14 = new Appointment(
                    pet2.getId(), // Luna
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 4, 9, 40),
                    "Vacunación múltiple",
                    "Aceptada",
                    "Segunda dosis"
            );
            dailyApp14.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp14);
            LOGGER.info("Created DAILY appointment 14: WED 04/12 09:40 - {}", dailyApp14.getMotivo());

            var dailyApp15 = new Appointment(
                    pet3.getId(), // Rocky
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 4, 16, 10),
                    "Revisión dental",
                    "Aceptada",
                    "Control dental rutina"
            );
            dailyApp15.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp15);
            LOGGER.info("Created DAILY appointment 15: WED 04/12 16:10 - {}", dailyApp15.getMotivo());

            // THURSDAY - 05/12/2025
            var dailyApp16 = new Appointment(
                    pet4.getId(), // Mia
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 5, 11, 25),
                    "Consulta dermatológica",
                    "Aceptada",
                    "Control de alergias"
            );
            dailyApp16.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp16);
            LOGGER.info("Created DAILY appointment 16: THU 05/12 11:25 - {}", dailyApp16.getMotivo());

            var dailyApp17 = new Appointment(
                    pet5.getId(), // Bruno
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 5, 15, 50),
                    "Control nutricional",
                    "Aceptada",
                    "Control de displasia"
            );
            dailyApp17.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp17);
            LOGGER.info("Created DAILY appointment 17: THU 05/12 15:50 - {}", dailyApp17.getMotivo());

            // FRIDAY - 06/12/2025
            var dailyApp18 = new Appointment(
                    pet6.getId(), // Coco
                    veterinary.getId(), // Ana Martinez (veterinary user)
                    LocalDateTime.of(2025, 12, 6, 8, 15),
                    "Consulta general",
                    "Aceptada",
                    "Chequeo semanal"
            );
            dailyApp18.setVeterinaryStatus("ACCEPTED");
            appointmentRepository.save(dailyApp18);
            LOGGER.info("Created DAILY appointment 18: FRI 06/12 08:15 - {}", dailyApp18.getMotivo());

            // Create notifications
            // Notifications for Pet Lover (petLover.getId())
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
                    "Recordatorio: Tienes una cita programada para el " + LocalDateTime.now().plusDays(2).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " a las 10:00 para Max.",
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
                    "Los resultados del análisis de sangre de Max están disponibles. Todo está en orden.",
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
                    "Tu cita ha sido confirmada para el " + LocalDateTime.now().plusDays(2).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " con la Dra. Ana Martinez.",
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

            // Notifications for Veterinary (veterinary.getId())
            // Notification 9 - Welcome for veterinary (read)
            var notification9 = new Notification(
                    veterinary.getId(),
                    "Bienvenida Dra. Ana Martinez. Sistema de gestión de citas veterinarias activado.",
                    "Bienvenida"
            );
            notification9.markAsRead();
            notificationRepository.save(notification9);
            LOGGER.info("Created notification 9: Welcome for veterinary user {}", veterinary.getId());

            // Notification 10 - New appointment request (unread)
            var notification10 = new Notification(
                    veterinary.getId(),
                    "Nueva solicitud de cita: Mia - Consulta por dermatitis para el " + LocalDateTime.now().plusDays(10).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".",
                    "RecordatorioCita"
            );
            notificationRepository.save(notification10);
            LOGGER.info("Created notification 10: New appointment request for veterinary {}", veterinary.getId());

            // Notification 11 - Upcoming appointment reminder (unread)
            var notification11 = new Notification(
                    veterinary.getId(),
                    "Recordatorio: Tienes una cita programada mañana con Max a las 10:00 - Chequeo de seguimiento.",
                    "RecordatorioCita"
            );
            notificationRepository.save(notification11);
            LOGGER.info("Created notification 11: Upcoming appointment reminder for veterinary {}", veterinary.getId());

            // Notification 12 - Completed appointment notification (read)
            var notification12 = new Notification(
                    veterinary.getId(),
                    "Has completado exitosamente la cita con Rocky. Registro guardado correctamente.",
                    "Confirmacion"
            );
            notification12.markAsRead();
            notificationRepository.save(notification12);
            LOGGER.info("Created notification 12: Completed appointment for veterinary {}", veterinary.getId());

            // Notification 13 - Multiple appointments today (unread)
            var notification13 = new Notification(
                    veterinary.getId(),
                    "Tienes 3 citas aceptadas para los próximos 7 días. Revisa tu agenda.",
                    "AnuncioGeneral"
            );
            notificationRepository.save(notification13);
            LOGGER.info("Created notification 13: Schedule reminder for veterinary {}", veterinary.getId());

            // Notification 14 - Follow-up recommendation (unread)
            var notification14 = new Notification(
                    veterinary.getId(),
                    "Sugerencia: Programa cita de seguimiento para Max después de su vacunación del día 15.",
                    "RecordatorioSalud"
            );
            notificationRepository.save(notification14);
            LOGGER.info("Created notification 14: Follow-up recommendation for veterinary {}", veterinary.getId());

            LOGGER.info("Database seeding completed successfully!");
        };
    }
}
