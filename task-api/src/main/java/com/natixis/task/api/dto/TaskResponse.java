package com.natixis.task.api.dto;

import java.util.UUID;

/**
 * Représentation REST d'une tâche.
 */
public record TaskResponse(UUID id, String label, String description, boolean completed) {}
