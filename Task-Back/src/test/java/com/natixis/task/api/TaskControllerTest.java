package com.natixis.task.api;

import com.natixis.task.api.TaskController;
import com.natixis.task.application.usecase.TaskUseCases;
import com.natixis.task.domain.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    TaskUseCases uc;

    @Test
    void list_returns_all_tasks() throws Exception {
        var t = new Task(UUID.randomUUID(), "A", "B", false);
        Mockito.when(uc.listAll()).thenReturn(List.of(t));

        mvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].label").value("A"));
    }

    @Test
    void get_returns_404_when_absent() throws Exception {
        Mockito.when(uc.getById(any())).thenReturn(Optional.empty());
        mvc.perform(get("/api/tasks/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_validates_label_and_returns_201() throws Exception {
        var t = new Task(UUID.randomUUID(), "X", "", false);
        Mockito.when(uc.add(eq("X"), eq(""), eq(false))).thenReturn(t);

        var payload = new ObjectMapper()
                .createObjectNode()
                .put("label", "X")
                .put("description", "");

        mvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload.toString()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.label").value("X"));
    }
}
