package com.dogimax.dogimaxapi.iam.application.internal.outboundservices.tokens;

/**
 * TokenService interface
 * This interface is used to generate and validate JWT tokens
 */
public interface TokenService {
    /**
     * Generate a token for a username
     * @param username the username
     * @return String the JWT token
     */
    String generateToken(String username);

    /**
     * Validate a token
     * @param token the JWT token
     * @return String the username
     */
    String getUsernameFromToken(String token);

    /**
     * Validate a token
     * @param token the JWT token
     * @return boolean true if the token is valid, false otherwise
     */
    boolean validateToken(String token);
}
