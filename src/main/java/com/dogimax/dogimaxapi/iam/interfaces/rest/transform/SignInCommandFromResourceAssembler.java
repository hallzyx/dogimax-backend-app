package com.dogimax.dogimaxapi.iam.interfaces.rest.transform;

import com.dogimax.dogimaxapi.iam.domain.model.commands.SignInCommand;
import com.dogimax.dogimaxapi.iam.interfaces.rest.resources.SignInResource;

/**
 * Assembler to convert a SignInResource to a SignInCommand
 */
public class SignInCommandFromResourceAssembler {
    /**
     * Converts a SignInResource to a SignInCommand
     * @param resource the SignInResource
     * @return the SignInCommand
     */
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.email(), resource.password());
    }
}
