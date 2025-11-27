package com.dogimax.dogimaxapi.pet_management.application.internal.commandservices;

import com.dogimax.dogimaxapi.pet_management.domain.model.aggregates.Pet;
import com.dogimax.dogimaxapi.pet_management.domain.model.commands.CreatePetCommand;
import com.dogimax.dogimaxapi.pet_management.domain.model.commands.DeletePetCommand;
import com.dogimax.dogimaxapi.pet_management.domain.model.commands.UpdatePetCommand;
import com.dogimax.dogimaxapi.pet_management.domain.services.PetCommandService;
import com.dogimax.dogimaxapi.pet_management.infrastructure.persistence.jpa.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Pet command service implementation
 * <p>
 *     This class implements the {@link PetCommandService} interface and provides the implementation for the
 *     pet commands.
 * </p>
 */
@Service
@Transactional
public class PetCommandServiceImpl implements PetCommandService {

    private final PetRepository petRepository;

    public PetCommandServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * Handle the create pet command
     * <p>
     *     This method handles the {@link CreatePetCommand} command and returns the created pet.
     * </p>
     * @param command the create pet command containing pet data
     * @return the created pet
     */
    @Override
    public Pet handle(CreatePetCommand command) {
        var pet = new Pet(
                command.name(),
                command.species(),
                command.breed(),
                command.birthDate(),
                command.gender(),
                command.weight(),
                command.color(),
                command.isNeutered(),
                command.observations(),
                command.photo(),
                command.userId()
        );
        return petRepository.save(pet);
    }

    /**
     * Handle the update pet command
     * <p>
     *     This method handles the {@link UpdatePetCommand} command and returns the updated pet.
     * </p>
     * @param command the update pet command containing pet data
     * @return an optional containing the updated pet if found
     */
    @Override
    public Optional<Pet> handle(UpdatePetCommand command) {
        return petRepository.findById(command.id())
                .map(pet -> {
                    if (command.name() != null) pet.setName(command.name());
                    if (command.species() != null) pet.setSpecies(command.species());
                    if (command.breed() != null) pet.setBreed(command.breed());
                    if (command.birthDate() != null) pet.setBirthDate(command.birthDate());
                    if (command.gender() != null) pet.setGender(command.gender());
                    if (command.weight() != null) pet.setWeight(command.weight());
                    if (command.color() != null) pet.setColor(command.color());
                    if (command.isNeutered() != null) pet.setIsNeutered(command.isNeutered());
                    if (command.observations() != null) pet.setObservations(command.observations());
                    if (command.photo() != null) pet.setPhoto(command.photo());
                    return petRepository.save(pet);
                });
    }

    /**
     * Handle the delete pet command
     * <p>
     *     This method handles the {@link DeletePetCommand} command and deactivates the pet.
     * </p>
     * @param command the delete pet command containing the pet id
     */
    @Override
    public void handle(DeletePetCommand command) {
        petRepository.findById(command.id())
                .ifPresent(pet -> {
                    pet.deactivate();
                    petRepository.save(pet);
                });
    }
}

