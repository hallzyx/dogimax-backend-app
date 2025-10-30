package com.dogimax.dogimaxapi.iam.domain.model.commands;

/**
 * Sign up command
 * @param nombre the user's first name
 * @param apellido the user's last name
 * @param email the user's email
 * @param telefono the user's phone number
 * @param password the user's password
 * @param rol the user's role
 */
public record SignUpCommand(
        String nombre,
        String apellido,
        String email,
        String telefono,
        String password,
        String rol
) {
}
