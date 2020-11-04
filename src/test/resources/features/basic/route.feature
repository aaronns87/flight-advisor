Feature: Route REST endpoint test

  Scenario: Creating a route & check if it exists
    Given Airline is created with code "2B"

    And City is created with name "London" in country "England"
    And Airport is created with name "London Airport", city name "London", iata "GKA", icao "AYGA", latitude "-6.081689834590001", longitude "145.391998291", altitude "5282", timeZone "0", dst "E", tz "Europe/London", type "AIRPORT", source "OurAirports"

    And City is created with name "Paris" in country "France"
    And Airport is created with name "Paris Airport", city name "Paris", iata "PKA", icao "PYGA", latitude "-2.081689834590001", longitude "80.391998291", altitude "1234", timeZone "1", dst "E", tz "Europe/Paris", type "AIRPORT", source "OurAirports"

    When Route is created for airline code "2B", source airport code "SA", source airport name "London Airport", destination airport code "DA", destination airport name "Paris Airport", code share "FALSE", stops "0", equipment "SF3", price "100.00"
    Then Route request is performed with status code 201
    And Route exists for airline code "2B", source airport code "SA", source airport name "London Airport", destination airport code "DA", destination airport name "Paris Airport", code share "FALSE", stops "0", equipment "SF3", price "100.00"