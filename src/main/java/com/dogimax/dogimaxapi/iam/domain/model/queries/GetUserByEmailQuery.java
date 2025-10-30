package com.dogimax.dogimaxapi.iam.domain.model.queries;

/**
 * Query to get a user by email
 * @param email the user email
 */
public record GetUserByEmailQuery(String email) {
}
