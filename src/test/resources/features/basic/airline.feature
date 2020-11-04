Feature: Airline REST endpoint test

  Scenario: Creating an airline & check if it exists
    When Airline is created with code "2A"
    Then Airline request is performed with status code 201
    And Airline exists with code "2A"
