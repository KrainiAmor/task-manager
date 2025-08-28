package com.natixis.task.bdd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration de test pour fournir RestTemplate.
 */
@Configuration
public class TestConfig {
    /** @return un RestTemplate simple pour les tests BDD */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
