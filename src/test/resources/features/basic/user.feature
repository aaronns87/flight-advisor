Feature: User REST endpoint test

  Scenario: Creating a user & check if it exists
    When User is created with role "ROLE_ADMIN", first name "Admin", last name "Admin", username "admin", password "password"
    Then User request is performed with status code 201
    And User exists with role "ROLE_ADMIN", first name "Admin", last name "Admin", username "admin"
