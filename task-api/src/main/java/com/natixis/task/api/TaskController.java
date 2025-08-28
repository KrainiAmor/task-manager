package com.natixis.task.api;

import com.natixis.task.api.dto.CreateTaskRequest;
import com.natixis.task.api.dto.TaskResponse;
import com.natixis.task.api.dto.UpdateStatusRequest;
import com.natixis.task.application.usecase.TaskUseCases;
import com.natixis.task.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Contrôleur REST exposant les opérations CRUD de tâches.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskUseCases useCases;

    /** Injection du service applicatif. */
    public TaskController(TaskUseCases useCases) {
        this.useCases = useCases;
    }

    /**
     * Liste les tâches, éventuellement filtrées par statut.
     * @param completed null pour tout lister, sinon true/false
     * @return liste de tâches
     */
    @GetMapping
    public List<TaskResponse> list(@RequestParam(value = "completed", required = false) Boolean completed) {
        var tasks = completed == null ? useCases.listAll() : useCases.listByCompleted(completed);
        return tasks.stream().map(TaskController::toDto).toList();
    }

    /**
     * Récupère une tâche par identifiant.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> get(@PathVariable UUID id) {
        return useCases.getById(id).map(t -> ResponseEntity.ok(toDto(t))).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle tâche.
     */
    @PostMapping
    public ResponseEntity<TaskResponse> create(@Validated @RequestBody CreateTaskRequest req) {
        var created = useCases.add(req.label(), req.description(), req.completed() != null && req.completed());
        return ResponseEntity.created(URI.create("/api/tasks/" + created.id())).body(toDto(created));
    }

    /**
     * Modifie le statut d'achèvement d'une tâche.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> setCompleted(@PathVariable UUID id, @Validated @RequestBody UpdateStatusRequest req) {
        return useCases.setCompleted(id, req.completed())
                .map(t -> ResponseEntity.ok(toDto(t)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /** Conversion domaine -> DTO. */
    private static TaskResponse toDto(Task t) {
        return new TaskResponse(t.id(), t.label(), t.description(), t.completed());
    }
}
