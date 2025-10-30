package com.dogimax.dogimaxapi.iam.interfaces.rest.resources;

/**
 * User resource
 * @param id the user id
 * @param nombre the user's first name
 * @param apellido the user's last name
 * @param email the user's email
 * @param telefono the user's phone number
 * @param fechaRegistro the user's registration date
 * @param rol the user's role
 */
public record UserResource(
        Long id,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String fechaRegistro,
        String rol
) {
}
