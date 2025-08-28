package com.natixis.task.application.usecase;

import com.natixis.task.application.port.TaskRepository;
import com.natixis.task.application.usecase.TaskUseCases;
import com.natixis.task.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitaires des cas d'utilisation.
 */
class TaskUseCasesTest {

    TaskUseCases uc;
    FakeRepo repo;

    /** Prépare un dépôt factice et le service. */
    @BeforeEach
    void setUp() {
        repo = new FakeRepo();
        uc = new TaskUseCases(repo);
    }

    /** Vérifie la création d'une tâche. */
    @Test
    void add_creates_task_with_given_fields() {
        var created = uc.add("Lire SOLID", "Relire SRP et OCP", false);
        assertThat(repo.store).containsKey(created.id());
        assertThat(created.label()).isEqualTo("Lire SOLID");
        assertThat(created.completed()).isFalse();
    }

    /** Vérifie la mise à jour du statut. */
    @Test
    void setCompleted_updates_status() {
        var t = uc.add("Test", null, false);
        uc.setCompleted(t.id(), true);
        assertThat(repo.store.get(t.id()).completed()).isTrue();
    }

    /** Dépôt factice en mémoire utilisé pour les tests. */
    static class FakeRepo implements TaskRepository {
        Map<UUID, Task> store = new HashMap<>();
        public List<Task> findAll(){ return new ArrayList<>(store.values());}
        public List<Task> findByCompleted(boolean c){ return store.values().stream().filter(t->t.completed()==c).toList();}
        public Optional<Task> findById(UUID id){ return Optional.ofNullable(store.get(id));}
        public Task save(Task task){ store.put(task.id(), task); return task;}
        public Task update(Task task){ store.put(task.id(), task); return task;}
    }
}
