package com.dogimax.dogimaxapi.iam.application.internal.queryservices;

import com.dogimax.dogimaxapi.iam.domain.model.aggregates.User;
import com.dogimax.dogimaxapi.iam.domain.model.queries.GetAllUsersQuery;
import com.dogimax.dogimaxapi.iam.domain.model.queries.GetUserByEmailQuery;
import com.dogimax.dogimaxapi.iam.domain.model.queries.GetUserByIdQuery;
import com.dogimax.dogimaxapi.iam.domain.services.UserQueryService;
import com.dogimax.dogimaxapi.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User query service implementation
 * <p>
 *     This class implements the {@link UserQueryService} interface and provides the implementation for the
 *     user queries.
 * </p>
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(query.email());
    }
}
