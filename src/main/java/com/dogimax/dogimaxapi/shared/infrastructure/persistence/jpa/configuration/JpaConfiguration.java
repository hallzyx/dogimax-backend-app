package com.dogimax.dogimaxapi.shared.infrastructure.persistence.jpa.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA Configuration
 * This class configures JPA and Spring Data JPA for the application.
 * The naming strategy is configured via application.properties
 * using: spring.jpa.properties.hibernate.physical_naming_strategy
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.dogimax.dogimaxapi")
@EntityScan(basePackages = "com.dogimax.dogimaxapi")
@EnableTransactionManagement
public class JpaConfiguration {
}

