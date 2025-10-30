package com.dogimax.dogimaxapi.iam.application.internal.commandservices;

import com.dogimax.dogimaxapi.iam.application.internal.outboundservices.hashing.HashingService;
import com.dogimax.dogimaxapi.iam.domain.model.aggregates.User;
import com.dogimax.dogimaxapi.iam.domain.model.commands.SignInCommand;
import com.dogimax.dogimaxapi.iam.domain.model.commands.SignUpCommand;
import com.dogimax.dogimaxapi.iam.domain.services.UserCommandService;
import com.dogimax.dogimaxapi.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the authenticated user.
     * </p>
     * @param command the sign-in command containing the email and password
     * @return an optional containing the user if authentication is successful
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<User> handle(SignInCommand command) {
        var user = userRepository.findByEmail(command.email());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        return user;
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the created user.
     * </p>
     * @param command the sign-up command containing user data
     * @return the created user
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email()))
            throw new RuntimeException("Email already exists");
        
        var user = new User(
                command.nombre(),
                command.apellido(),
                command.email(),
                command.telefono(),
                hashingService.encode(command.password()),
                command.rol() != null ? command.rol() : "USER"
        );
        userRepository.save(user);
        return userRepository.findByEmail(command.email());
    }
}
