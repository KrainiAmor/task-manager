package com.natixis.task.config;

import com.natixis.task.application.port.TaskRepository;
import com.natixis.task.application.usecase.TaskUseCases;
import com.natixis.task.domain.Task;
import com.natixis.task.infrastructure.InMemoryTaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

/**
 * Configuration.
 */
@Configuration
public class AppConfig {

    /**
     * Déclare un dépôt en mémoire avec un jeu d'exemples.
     */
    @Bean
    public TaskRepository taskRepository() {
        return new InMemoryTaskRepository(List.of(
                new Task(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"), "Tache 1", "Tache 1 description", false),
                new Task(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"), "tache 2", "Tache 2 description", true),
                new Task(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"), "tache 3", "Tache 3 description", false)
        ));
    }

    /**
     * Expose les cas d'utilisation en tant que service applicatif.
     */
    @Bean
    public TaskUseCases taskUseCases(TaskRepository repo) {
        return new TaskUseCases(repo);
    }
}
