package com.natixis.task.api.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Requête de mise à jour du statut d'une tâche.
 */
public record UpdateStatusRequest(@NotNull Boolean completed) {}
