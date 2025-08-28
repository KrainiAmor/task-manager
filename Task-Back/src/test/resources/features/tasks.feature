Feature: Manage tasks via REST API
  As a user
  I want to manage tasks
  So that I can track what to do

  Scenario: List existing tasks
    Given the API is running
    When I GET /api/tasks
    Then I receive a 200 response
    And the payload contains a non-empty list
