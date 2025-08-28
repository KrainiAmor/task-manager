package com.natixis.task.bdd;

import com.natixis.task.TaskApplication;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Définition des étapes BDD pour l'API des tâches.
 */
@SpringBootTest(classes = TaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskStepDefs {

    @LocalServerPort
    int port;

    @Autowired
    RestTemplate template;

    ResponseEntity<String> response;

    /** Vérifie que le contexte est démarré. */
    @Given("the API is running")
    public void api_running() {
        // Contexte Spring démarré via @SpringBootTest
    }

    /** Envoie une requête GET sur /api/tasks. */
    @When("I GET /api/tasks")
    public void i_get_tasks() throws RestClientException {
        response = template.getForEntity("http://localhost:" + port + "/api/tasks", String.class);
    }

    /** Vérifie le code HTTP attendu. */
    @Then("I receive a {int} response")
    public void i_receive_status(int status) {
        assertThat(response.getStatusCode().value()).isEqualTo(status);
    }

    /** Vérifie que la charge utile n'est pas vide. */
    @And("the payload contains a non-empty list")
    public void payload_non_empty() {
        assertThat(response.getBody()).contains("id");
    }
}
