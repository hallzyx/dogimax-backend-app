package com.dogimax.dogimaxapi.iam.interfaces.rest.transform;

import com.dogimax.dogimaxapi.iam.domain.model.aggregates.User;
import com.dogimax.dogimaxapi.iam.interfaces.rest.resources.UserResource;

/**
 * Assembler to convert a User entity to a UserResource
 */
public class UserResourceFromEntityAssembler {
    /**
     * Converts a User entity to a UserResource
     * @param entity the User entity
     * @return the UserResource
     */
    public static UserResource toResourceFromEntity(User entity) {
        return new UserResource(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getEmail(),
                entity.getTelefono(),
                entity.getFechaRegistro() != null ? entity.getFechaRegistro().toString() : null,
                entity.getRol()
        );
    }
}
