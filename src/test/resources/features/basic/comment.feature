Feature: Comment REST endpoint test

  Scenario: Creating a comment & check if it exists
    Given City is created with name "London" in country "England"
    When Comment is created for city name "London", with description "Dont fly due to fog"
    Then Comment request is performed with status code 201
    And Comment exists for city name "London", with description "Dont fly due to fog"
