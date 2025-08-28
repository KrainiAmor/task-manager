package com.natixis.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale Spring Boot
 */
@SpringBootApplication
public class TaskApplication {
    /**
     * Point d'entrée de l'application.
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
