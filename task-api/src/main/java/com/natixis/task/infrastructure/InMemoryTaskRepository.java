package com.natixis.task.infrastructure;

import com.natixis.task.application.port.TaskRepository;
import com.natixis.task.domain.Task;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implémentation en mémoire du port TaskRepository.
 * Thread-safe via ConcurrentHashMap.
 */
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<UUID, Task> data = new ConcurrentHashMap<>();

    /** Constructeur avec données de départ. */
    public InMemoryTaskRepository(Collection<Task> seed) {
        seed.forEach(t -> data.put(t.id(), t));
    }

    /** Constructeur vide. */
    public InMemoryTaskRepository() { this(List.of()); }

    @Override public List<Task> findAll() {
        return data.values().stream().sorted(Comparator.comparing(Task::label)).collect(Collectors.toList());
    }

    @Override public List<Task> findByCompleted(boolean completed) {
        return data.values().stream().filter(t -> t.completed() == completed)
                .sorted(Comparator.comparing(Task::label)).collect(Collectors.toList());
    }

    @Override public Optional<Task> findById(UUID id) { return Optional.ofNullable(data.get(id)); }

    @Override public Task save(Task task) {
        data.put(task.id(), task);
        return task;
    }

    @Override public Task update(Task task) {
        data.put(task.id(), task);
        return task;
    }
}
