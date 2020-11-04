Feature: Airport REST endpoint test

  Scenario: Creating an airport & check if it exists
    Given City is created with name "London" in country "England"
    When Airport is created with name "London Airport", city name "London", iata "GKA", icao "AYGA", latitude "-6.081689834590001", longitude "145.391998291", altitude "5282", timeZone "10", dst "U", tz "Pacific/Port_Moresby", type "AIRPORT", source "OurAirports"
    Then Airport request is performed with status code 201
    And Airport exists with name "London Airport", city name "London", iata "GKA", icao "AYGA", latitude "-6.081689834590001", longitude "145.391998291", altitude "5282", timeZone "10.0", dst "U", tz "Pacific/Port_Moresby", type "AIRPORT", source "OurAirports"
