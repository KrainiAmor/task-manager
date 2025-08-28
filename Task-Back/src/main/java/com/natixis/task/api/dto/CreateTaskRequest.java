package com.natixis.task.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Requête de création d'une tâche.
 */
public record CreateTaskRequest(
        @NotBlank String label,
        String description,
        Boolean completed
) {}
