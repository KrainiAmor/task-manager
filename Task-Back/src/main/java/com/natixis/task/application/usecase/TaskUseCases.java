package com.natixis.task.application.usecase;

import com.natixis.task.application.port.TaskRepository;
import com.natixis.task.domain.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Cas d'utilisation applicatifs pour manipuler les tâches.
 * Respecte le DIP (dépend de l'interface TaskRepository).
 */
public class TaskUseCases {

    private final TaskRepository repository;

    /**
     * Construit le service applicatif.
     * @param repository port de persistance
     */
    public TaskUseCases(TaskRepository repository) {
        this.repository = repository;
    }

    /** @return toutes les tâches */
    public List<Task> listAll() { return repository.findAll(); }

    /**
     * Liste selon le statut d'achèvement.
     * @param completed filtre
     * @return tâches
     */
    public List<Task> listByCompleted(boolean completed) { return repository.findByCompleted(completed); }

    /**
     * Récupère une tâche par id.
     * @param id identifiant
     * @return option de tâche
     */
    public Optional<Task> getById(UUID id) { return repository.findById(id); }

    /**
     * Ajoute une nouvelle tâche.
     * @param label intitulé
     * @param description description
     * @param completed statut initial
     * @return tâche créée
     */
    public Task add(String label, String description, boolean completed) {
        var task = new Task(UUID.randomUUID(), label, description, completed);
        return repository.save(task);
    }

    /**
     * Modifie le statut d'une tâche existante.
     * @param id identifiant
     * @param completed nouveau statut
     * @return tâche mise à jour si existante
     */
    public Optional<Task> setCompleted(UUID id, boolean completed) {
        return repository.findById(id).map(t -> repository.update(t.withCompleted(completed)));
    }
}
