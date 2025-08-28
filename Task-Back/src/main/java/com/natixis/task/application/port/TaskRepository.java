package com.natixis.task.application.port;

import com.natixis.task.domain.Task;
import java.util.*;

/**
 * Port de persistance pour les tâches (contrat hexagonal).
 */
public interface TaskRepository {
    /** @return toutes les tâches triées. */
    List<Task> findAll();
    /**
     * Liste par statut.
     * @param completed filtre statut
     * @return tâches
     */
    List<Task> findByCompleted(boolean completed);
    /**
     * Recherche par identifiant.
     * @param id identifiant
     * @return option de tâche
     */
    Optional<Task> findById(UUID id);
    /**
     * Sauvegarde une nouvelle tâche.
     * @param task entité
     * @return entité persistée
     */
    Task save(Task task);
    /**
     * Met à jour une tâche existante.
     * @param task entité
     * @return entité mise à jour
     */
    Task update(Task task);
}
