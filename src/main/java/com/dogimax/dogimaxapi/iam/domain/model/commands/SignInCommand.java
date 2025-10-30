package com.dogimax.dogimaxapi.iam.domain.model.commands;

/**
 * Sign in command
 * @param email the user's email
 * @param password the user's password
 */
public record SignInCommand(String email, String password) {
}
