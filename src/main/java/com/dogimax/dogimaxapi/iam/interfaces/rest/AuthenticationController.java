package com.dogimax.dogimaxapi.iam.interfaces.rest;

import com.dogimax.dogimaxapi.iam.domain.services.UserCommandService;
import com.dogimax.dogimaxapi.iam.infrastructure.tokens.jwt.BearerTokenService;
import com.dogimax.dogimaxapi.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.dogimax.dogimaxapi.iam.interfaces.rest.resources.SignInResource;
import com.dogimax.dogimaxapi.iam.interfaces.rest.resources.SignUpResource;
import com.dogimax.dogimaxapi.iam.interfaces.rest.resources.UserResource;
import com.dogimax.dogimaxapi.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.dogimax.dogimaxapi.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.dogimax.dogimaxapi.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.dogimax.dogimaxapi.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/authentication/sign-in</li>
 *         <li>POST /api/v1/authentication/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Available Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;
    private final BearerTokenService tokenService;

    public AuthenticationController(UserCommandService userCommandService, BearerTokenService tokenService) {
        this.userCommandService = userCommandService;
        this.tokenService = tokenService;
    }

    /**
     * Handles the sign-in request.
     * <p>
     *     This endpoint authenticates a user with their email and password.
     *     If successful, returns all user information (except password) and a JWT token.
     * </p>
     * @param signInResource the sign-in request body containing email and password
     * @return the authenticated user resource with JWT token
     */
    @PostMapping("/sign-in")
    @Operation(summary = "Sign-in", description = "Sign-in with the provided credentials. Returns user information and JWT token if successful.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found or invalid credentials.")})
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedUser = userCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var user = authenticatedUser.get();
        var token = tokenService.generateToken(user.getEmail());
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(user, token);
        return ResponseEntity.ok(authenticatedUserResource);
    }

    /**
     * Handles the sign-up request.
     * <p>
     *     This endpoint registers a new user in the system.
     * </p>
     * @param signUpResource the sign-up request body containing user information
     * @return the created user resource
     */
    @PostMapping("/sign-up")
    @Operation(summary = "Sign-up", description = "Register a new user with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request - Email already exists or invalid data.")})
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
}
