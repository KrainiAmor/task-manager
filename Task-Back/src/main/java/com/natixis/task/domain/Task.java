package com.natixis.task.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Entité du domaine représentant une Tâche.
 * Immuable pour favoriser la prédictibilité.
 */
public final class Task {
    private final UUID id;
    private final String label;
    private final String description;
    private final boolean completed;

    /**
     * Crée une tâche.
     * @param id identifiant unique
     * @param label intitulé non nul
     * @param description description éventuelle
     * @param completed statut d'achèvement
     */
    public Task(UUID id, String label, String description, boolean completed) {
        this.id = Objects.requireNonNull(id, "id");
        this.label = Objects.requireNonNull(label, "label");
        this.description = description == null ? "" : description;
        this.completed = completed;
    }

    /** @return l'identifiant */
    public UUID id() { return id; }
    /** @return l'intitulé */
    public String label() { return label; }
    /** @return la description */
    public String description() { return description; }
    /** @return true si la tâche est terminée */
    public boolean completed() { return completed; }

    /**
     * Retourne une nouvelle instance avec un statut modifié.
     * @param completed nouveau statut
     * @return nouvelle tâche
     */
    public Task withCompleted(boolean completed) {
        return new Task(this.id, this.label, this.description, completed);
    }

    /** @return nouvelle instance avec nouvel intitulé */
    public Task withLabel(String label) {
        return new Task(this.id, label, this.description, this.completed);
    }

    /** @return nouvelle instance avec nouvelle description */
    public Task withDescription(String description) {
        return new Task(this.id, this.label, description, this.completed);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id.equals(task.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
