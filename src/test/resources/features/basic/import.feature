Feature: Import REST endpoint test

  Scenario: Importing airports & check if exists
    When Airports are imported with content "1,\"Goroka Airport\",\"Goroka\",\"Papua New Guinea\",\"GKA\",\"AYGA\",-6.081689834590001,145.391998291,5282,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\""
    And Import request is performed with status code 202
    Then City exists with name "Goroka" in country "Papua New Guinea"
    And Airport exists with name "Goroka Airport", city name "Goroka", iata "GKA", icao "AYGA", latitude "-6.081689834590001", longitude "145.391998291", altitude "5282", timeZone "10", dst "U", tz "Pacific/Port_Moresby", type "AIRPORT", source "OurAirports"

  Scenario: Importing routes & check if exists
    When Airports are imported with content "1,\"Goroka Airport\",\"Goroka\",\"Papua New Guinea\",\"GKA\",\"AYGA\",-6.081689834590001,145.391998291,5282,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\""
    And Airports are imported with content "2,\"Madang Airport\",\"Madang\",\"Papua New Guinea\",\"MAG\",\"AYMD\",-5.20707988739,145.789001465,20,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\""
    And Import request is performed with status code 202
    When Routes are imported with content "2B,410,GKA,1,MAG,2,,0,CR2,95.87"
    Then Import request is performed with status code 202
    And Airline exists with code "2B"
    And Route exists for airline code "2B", source airport code "GKA", source airport name "Goroka Airport", destination airport code "MAG", destination airport name "Madang Airport", code share "FALSE", stops "0", equipment "CR2", price "95.87"
