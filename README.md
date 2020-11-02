# Flight advisor

Flight advisor coding challenge

## Flight advisor API

### User roles

#### Administrator

The administrator is able to:
* Add cities. For each city, the name, country and description must be provided.
* Import the data for the airports and routes from the datasets given in an attachment. Each airport contains city and country name. Don’t import airports and routes if the city doesn’t exist.

#### Regular user

* Get all the cities (all the comments should be returned, or if specified, only the x latest comments).
*	Search for cities by name (all the comments should be returned, or if specified, only the x latest comments).
*	Add a comment for the city.
*	Delete a comment.
*	Update a comment.
*	Traveling from city A to B, can include many stops, many airports, and it can cost a lot. Find the cheapest flight from city A to B, return all the routes included, total price and length. For simplification, each returned route can contain the name of the source and destination cities and price.

* Besides comment’s description, each comment should have created and modified date.

### Data structure & description

#### User

Every user has: first name, last name, user name, password and salt.
The regular user has to register in order to be able to use a Flight advisor.

* id - INTEGER (autoincrement)
* firstName - VARCHAR
* lastName - VARCHAR
* userName - VARCHAR
* password - VARCHAR
* salt - VARCHAR

#### City

* id - INTEGER (autoincrement)
* name - VARCHAR
* country - VARCHAR

#### Comment

* id - INTEGER (autoincrement)
* cityId - INTEGER
* description - VARCHAR
* createdDate - TIMESTAMP
* modifiedDate - TIMESTAMP
* modifiedBy - VARCHAR

#### Route

Airline - 2-letter (IATA) or 3-letter (ICAO) code of the airline.
Airline ID - Identifier for airline.
Source airport - 3-letter (IATA) or 4-letter (ICAO) code of the source airport.
Source airport ID - Identifier for source airport.
Destination airport - 3-letter (IATA) or 4-letter (ICAO) code of the destination airport.
Destination airport ID - Unique OpenFlights identifier for destination airport.
Codeshare - "Y" if this flight is a codeshare, empty otherwise.
Stops - Number of stops on this flight ("0" for direct).
Equipment - 3-letter codes for plane type(s) generally used on this flight, separated by spaces.
Price - Flight cost.

* airline - VARCHAR
* airlineId - INTEGER
* sourceAirport - VARCHAR
* sourceAirportId - INTEGER
* destinationAirport - VARCHAR
* destinationAirportId - INTEGER
* codeShare - VARCHAR(1) NULLABLE
* stops - INTEGER, DEFAULT 0
* equipment - VARCHAR(3)
* price - NUMBER

#### Airport

Airport ID - Identifier for this airport.
Name - Name of airport.
City - Main city served by airport.
Country - Country or territory where airport is located.
IATA - 3-letter IATA code. Null if not assigned/unknown.
ICAO - 4-letter ICAO code. Null if not assigned.
Latitude - Decimal degrees, usually to six significant digits.
Longitude - Decimal degrees, usually to six significant digits.
Altitude - In feet.
Timezone - Hours offset from UTC. 
DST - Daylight savings time. One of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown).
Tz - database time zone	Timezone in "tz" (Olson) format, eg. "America/Los_Angeles".
Type - Type of the airport. 
Source - Source of this data.

* airportId - INTEGER (autoincrement)
* name - VARCHAR
* city - VARCHAR
* country - VARCHAR
* iata - VARCHAR(3) NULLABLE
* icao - VARCHAR(4) NULLABLE
* latitude - FLOAT
* longitude - FLOAT
* altitude - FLOAT
* timeZone - INTEGER
* dst - VARCHAR(1)
* tz - VARCHAR
* type - VARCHAR, ENUM
* source - VARCHAR

### REST API

Rest API documentation is available VIA swagger.

### Conclusion
