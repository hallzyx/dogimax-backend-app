package com.dogimax.dogimaxapi.iam.interfaces.rest.resources;

/**
 * AuthenticatedUserResource
 * This resource represents the authenticated user response with a JWT token
 */
public record AuthenticatedUserResource(
        Long id,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String fechaRegistro,
        String rol,
        String token
) {
}
