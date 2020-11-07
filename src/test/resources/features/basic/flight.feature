Feature: Flights REST endpoint test

  Scenario: Search cheapest flight
    Given Airline is created with code "1B"
    And Airline is created with code "2B"
    And Airline is created with code "3B"

    And City is created with name "London" in country "England"
    And Airport is created with name "London Airport", city name "London", iata "GKA", icao "AYGA", latitude "-6.081689834590001", longitude "145.391998291", altitude "5282", timeZone "0", dst "E", tz "Europe/London", type "AIRPORT", source "OurAirports"

    And City is created with name "Paris" in country "France"
    And Airport is created with name "Paris Airport", city name "Paris", iata "PKA", icao "PYGA", latitude "-2.081689834590001", longitude "80.391998291", altitude "1234", timeZone "1", dst "E", tz "Europe/Paris", type "AIRPORT", source "OurAirports"

    And City is created with name "Belgrade" in country "Serbia"
    And Airport is created with name "Belgrade Airport", city name "Belgrade", iata "BGD", icao "BGDA", latitude "-1.081689834590001", longitude "50.391998291", altitude "10", timeZone "1", dst "E", tz "Europe/Paris", type "AIRPORT", source "OurAirports"

    And City is created with name "Sofia" in country "Bulgaria"
    And Airport is created with name "Sofia Airport", city name "Sofia", iata "SAI", icao "SAIR", latitude "-1.082689834590001", longitude "40.391998291", altitude "50", timeZone "1", dst "E", tz "Europe/Paris", type "AIRPORT", source "OurAirports"

    And Route is created for airline code "1B", source airport code "SA1", source airport name "London Airport", destination airport code "DA1", destination airport name "Paris Airport", code share "FALSE", stops "0", equipment "SF1", price "30.00"
    And Route is created for airline code "2B", source airport code "SA2", source airport name "Paris Airport", destination airport code "DA2", destination airport name "Belgrade Airport", code share "FALSE", stops "0", equipment "SF2", price "40.00"
    And Route is created for airline code "3B", source airport code "SA3", source airport name "Belgrade Airport", destination airport code "DA3", destination airport name "Sofia Airport", code share "FALSE", stops "0", equipment "SF3", price "50.00"

    When Cheapest flight is searched for source city "London", country "England", destination city "Sofia", country "Bulgaria"

    Then Found 1 cheapest flight chain options
    And Flight chain "0" has total price "120.00" and hops "3"
    And Flight chain "0", flight chain stop "0" airline code is "1B" and equipment is "SF1"
    And Flight chain "0", flight chain stop "0" embark city "London", country "England", airport "London Airport", airport code "SA1" and disembark city "Paris", country "France", airport "Paris Airport", airport code "DA1"
    And Flight chain "0", flight chain stop "1" airline code is "2B" and equipment is "SF2"
    And Flight chain "0", flight chain stop "1" embark city "Paris", country "France", airport "Paris Airport", airport code "SA2" and disembark city "Belgrade", country "Serbia", airport "Belgrade Airport", airport code "DA2"
    And Flight chain "0", flight chain stop "2" airline code is "3B" and equipment is "SF3"
    And Flight chain "0", flight chain stop "2" embark city "Belgrade", country "Serbia", airport "Belgrade Airport", airport code "SA3" and disembark city "Sofia", country "Bulgaria", airport "Sofia Airport", airport code "DA3"
