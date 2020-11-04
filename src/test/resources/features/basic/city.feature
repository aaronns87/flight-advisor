Feature: City REST endpoint test

  Scenario: Creating a city & check if it exists
    When City is created with name "London" in country "England"
    Then City request is performed with status code 201
    And City exists with name "London" in country "England"

  Scenario: Search for a city
    When City is created with name "Belgrade" in country "Serbia"
    Then City request is performed with status code 201
    And City with name "Belg" is found in country "Serbia"