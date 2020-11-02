# Flight advisor

Flight advisor coding challenge

Create well documented easily runnable REST api for managing airports and flight routes. It is important to have two distinct user roles, ADMINISTRATOR and USER (regular user). Administrator can perform CRUD operations on CITIES, AIRPORTS and FLIGHTS and also import data from files. Regular user (USER) can search cities, perform CRUD operations on COMMENTS and perform searches for travel info between city A and B. 

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

* id - INTEGER (autoincrement) - 0
* firstName - VARCHAR - "John"
* lastName - VARCHAR - "Doe"
* userName - VARCHAR - "john.doe"
* password - VARCHAR - "encrypted"
* salt - VARCHAR

#### City

* id - INTEGER (autoincrement) - 0
* name - VARCHAR - "Goroka"
* country - VARCHAR - "Papua New Guinea"

#### Comment

* id - INTEGER (autoincrement) - 0
* cityId - INTEGER - 0
* description - TEXT - "Some comment that can be long"
* createdDate - TIMESTAMP - "2020-11-02T10:00:00"
* modifiedDate - TIMESTAMP - "2020-11-02T11:00:00"

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

* id - INTEGER (autoincrement) - 0
* name - VARCHAR - "Goroka Airport"
* city - VARCHAR - "Goroka"
* country - VARCHAR - "Papua New Guinea"
* iata - VARCHAR(3) NULLABLE - "GKA"
* icao - VARCHAR(4) NULLABLE - "AYGA"
* latitude - FLOAT - "-6.081689834590001"
* longitude - FLOAT - "145.391998291"
* altitude - FLOAT - "5282"
* timeZone - INTEGER - "10"
* dst - VARCHAR(1) - "U"
* tz - VARCHAR - "Pacific/Port_Moresby"
* type - VARCHAR, ENUM - "airport"
* source - VARCHAR - "OurAirports"

#### Airline

* id - INTEGER (autoincrement) - 0

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

* id - INTEGER (autoincrement) - 0
* airline - VARCHAR - "2B"
* airlineId - INTEGER - 23
* sourceAirport - VARCHAR - "AER"
* sourceAirportId - INTEGER - 234
* destinationAirport - VARCHAR - "KZN"
* destinationAirportId - INTEGER - 245
* codeShare - VARCHAR(1) NULLABLE - ""
* stops - INTEGER, DEFAULT 0 - "0"
* equipment - VARCHAR(3) - "CR2"
* price - NUMBER - "95.87"

### REST API

Rest API documentation is available VIA swagger.

### Conclusion
