package com.dogimax.dogimaxapi.iam.domain.model.queries;

/**
 * Query to get a user by id
 * @param userId the user id
 */
public record GetUserByIdQuery(Long userId) {
}
