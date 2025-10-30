package com.dogimax.dogimaxapi.iam.interfaces.rest.transform;

import com.dogimax.dogimaxapi.iam.domain.model.aggregates.User;
import com.dogimax.dogimaxapi.iam.interfaces.rest.resources.AuthenticatedUserResource;

import java.time.format.DateTimeFormatter;

/**
 * AuthenticatedUserResourceFromEntityAssembler
 * This class is used to assemble an AuthenticatedUserResource from a User entity and a JWT token
 */
public class AuthenticatedUserResourceFromEntityAssembler {
    /**
     * Assemble an AuthenticatedUserResource from a User entity and a JWT token
     * @param entity The User entity
     * @param token The JWT token
     * @return The AuthenticatedUserResource
     */
    public static AuthenticatedUserResource toResourceFromEntity(User entity, String token) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return new AuthenticatedUserResource(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getEmail(),
                entity.getTelefono(),
                entity.getFechaRegistro() != null ? entity.getFechaRegistro().format(formatter) : null,
                entity.getRol(),
                token
        );
    }
}
