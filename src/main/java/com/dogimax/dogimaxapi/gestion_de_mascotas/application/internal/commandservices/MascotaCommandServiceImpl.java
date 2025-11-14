package com.dogimax.dogimaxapi.gestion_de_mascotas.application.internal.commandservices;

import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.aggregates.Mascota;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands.CreateMascotaCommand;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands.DeleteMascotaCommand;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.model.commands.UpdateMascotaCommand;
import com.dogimax.dogimaxapi.gestion_de_mascotas.domain.services.MascotaCommandService;
import com.dogimax.dogimaxapi.gestion_de_mascotas.infrastructure.persistence.jpa.repositories.MascotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Mascota command service implementation
 * <p>
 *     This class implements the {@link MascotaCommandService} interface and provides the implementation for the
 *     mascota commands.
 * </p>
 */
@Service
@Transactional
public class MascotaCommandServiceImpl implements MascotaCommandService {

    private final MascotaRepository mascotaRepository;

    public MascotaCommandServiceImpl(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    /**
     * Handle the create mascota command
     * <p>
     *     This method handles the {@link CreateMascotaCommand} command and returns the created mascota.
     * </p>
     * @param command the create mascota command containing mascota data
     * @return the created mascota
     */
    @Override
    public Mascota handle(CreateMascotaCommand command) {
        var mascota = new Mascota(
                command.nombre(),
                command.especie(),
                command.raza(),
                command.fechaNacimiento(),
                command.sexo(),
                command.peso(),
                command.unidadPeso(),
                command.descripcion(),
                command.fotoUrl(),
                command.userId()
        );
        return mascotaRepository.save(mascota);
    }

    /**
     * Handle the update mascota command
     * <p>
     *     This method handles the {@link UpdateMascotaCommand} command and returns the updated mascota.
     * </p>
     * @param command the update mascota command containing mascota data
     * @return an optional containing the updated mascota if found
     */
    @Override
    public Optional<Mascota> handle(UpdateMascotaCommand command) {
        return mascotaRepository.findById(command.id())
                .map(mascota -> {
                    if (command.nombre() != null) mascota.setNombre(command.nombre());
                    if (command.especie() != null) mascota.setEspecie(command.especie());
                    if (command.raza() != null) mascota.setRaza(command.raza());
                    if (command.fechaNacimiento() != null) mascota.setFechaNacimiento(command.fechaNacimiento());
                    if (command.sexo() != null) mascota.setSexo(command.sexo());
                    if (command.peso() != null) mascota.setPeso(command.peso());
                    if (command.unidadPeso() != null) mascota.setUnidadPeso(command.unidadPeso());
                    if (command.descripcion() != null) mascota.setDescripcion(command.descripcion());
                    if (command.fotoUrl() != null) mascota.setFotoUrl(command.fotoUrl());
                    return mascotaRepository.save(mascota);
                });
    }

    /**
     * Handle the delete mascota command
     * <p>
     *     This method handles the {@link DeleteMascotaCommand} command and deactivates the mascota.
     * </p>
     * @param command the delete mascota command containing the mascota id
     */
    @Override
    public void handle(DeleteMascotaCommand command) {
        mascotaRepository.findById(command.id())
                .ifPresent(mascota -> {
                    mascota.desactivar();
                    mascotaRepository.save(mascota);
                });
    }
}

