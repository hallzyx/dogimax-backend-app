package com.dogimax.dogimaxapi.iam.interfaces.rest.transform;

import com.dogimax.dogimaxapi.iam.domain.model.commands.SignUpCommand;
import com.dogimax.dogimaxapi.iam.interfaces.rest.resources.SignUpResource;

/**
 * Assembler to convert a SignUpResource to a SignUpCommand
 */
public class SignUpCommandFromResourceAssembler {
    /**
     * Converts a SignUpResource to a SignUpCommand
     * @param resource the SignUpResource
     * @return the SignUpCommand
     */
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(
                resource.nombre(),
                resource.apellido(),
                resource.email(),
                resource.telefono(),
                resource.password(),
                resource.rol()
        );
    }
}
