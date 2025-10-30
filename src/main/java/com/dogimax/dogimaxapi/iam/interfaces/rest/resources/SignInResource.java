package com.dogimax.dogimaxapi.iam.interfaces.rest.resources;

/**
 * Sign in resource
 * @param email the user's email
 * @param password the user's password
 */
public record SignInResource(String email, String password) {
}
