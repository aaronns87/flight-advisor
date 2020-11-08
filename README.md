# Flight advisor

Flight advisor coding challenge

Create well documented easily runnable REST api for managing airports and flight routes. It is important to have two distinct user roles, ADMINISTRATOR and USER (regular user). Administrator can perform CRUD operations on CITIES, AIRPORTS and FLIGHTS and also import data from files. Regular user (USER) can search cities, perform CRUD operations on COMMENTS and perform searches for travel info between city A and B. 

## Environment & Dependencies

* Java Version 14
* Lombok
* Spring
* Flyway
* Postgres
* SpringDoc OpenApi
* Spring Security
* JPA
* JUnit
* Mockito
* Cucumber
* Gradle
* Docker, docker-compose

## Setup & Run

* Build `./gradlew clean build`
* Boot run `./gradlew bootRun` *Requires running database

### Demo profile

Application is running in `demo` profile by default which is inserting two users into database:
* Regular user, role USER, username: user, password: user
* Admin user, role ADMIN, username: admin, password: admin

### Docker compose

After performing build `./gradlew clean build`.

Spin up docker images for application and database.

* Run `docker-compose up -d` (or `docker-compose up` to monitor logs in terminal)
* Stop `docker-compose down`

Application runs on `http://localhost:8080` for convenience.

### Integration tests

Check Gerkin tests in `test/features` to see the scenarios.

Tests are not as detailed as I would like in normal cases, but I don't have time to make them with full coverage.
Tests are covering the most important scenarios.

Tests are blackbox testing the application via rest endpoints. No mocks are set up for integration tests.

* Run `./gradlew integrationTests`

## Swagger

Location `[HOST]/swagger-ui.html`

e.g. `http://localhost:8080/swagger-ui.html`

## Postman collection

There is a Postman collection file available under:

`./postman/FlightAdvisor.postman_collection.json` 

The most useful request definitions are available there as templates.

## Import

* Use properly formatted CSV files
* Quote: "
* Delimiter: ,
* Null string: \N
* Required basic auth, user with ADMIN role:

Endpoints:
* Airports: `POST /admin/import/airports`, multipart form data `file`, supported format `csv`, max size 4MB

* Routes: `POST /admin/import/routes`, multipart form data `file`, supported format `csv`, max size 4MB

* NOTE: Import emphises data integrity and validity over speed. When importing airports cities are created, when importing routes, the airport data is validated, airlines are created etc. Route import can take a bit longer because of great number of records being imported.

## The Cheapest flight SEARCH explanation

The entire algorithm for the cheapest flight search is performed via one SQL query which can be found 
as named query in Route entity class file.

The algorithm relies on the `source_city_mapping` and `destination_city_mapping` which are auto generated sequences on 
`city` entity. Because the requirement stated that the search is performed by providing `source` and `destination`
cities,once the route is created in db the `source_city_mapping` and `destination_city_mapping` are populated by the reference
between `route -> airport`, and `airport -> city`.

The SQL query is basically a recursive query that starts with the search on `source_city_mapping` and recursively 
expands the search to the nearest nodes (`destination_city_mapping`). The recursion is stopped by either reaching the
`destination_city_mapping` or by reaching the maximum depth (`hops`).

The returned results are ordered by the `total_price` ascending (which is calculated during recursive expansion) and the 
number of results is limited by `maxLimit` parameter.

### SQL
```
WITH RECURSIVE flight_chain(
  source_city_mapping, -- Source city mapping id
  destination_city_mapping, -- Destination city mapping id
  total_price, -- Total calculated price
  sources, -- Sources array of source city mappings (needed for circular check)
  destinations, -- Destinations array of destination city mappings (needed for circular check)
  routes, -- Array of route ids in the chain
  hops -- Hops in the chain, the deeper the recursion goes, the greater the hops number
) AS (      
        SELECT -- Root query
          r.source_city_mapping,
          r.destination_city_mapping,
          r.price as total_price,
          ARRAY[r.source_city_mapping] AS sources, -- Sources is array
          ARRAY[r.destination_city_mapping] AS destinations, -- Destinations is array
          ARRAY[r.id::TEXT] AS routes, -- Routes is array
          1 AS hops -- First hop (depth) is 1
        FROM route AS r       
        WHERE       
          source_city_mapping = :sourceAirportMapping -- Source city mapping id, begining of recursion
      UNION ALL      
        SELECT -- Recursive expansion
          r.source_city_mapping,
          r.destination_city_mapping,
          fc.total_price + r.price AS total_price, -- Add up price with new route
          sources || r.source_city_mapping AS sources, -- Append source mapping
          destinations || r.destination_city_mapping AS destinations, -- Append destination mapping
          routes || r.id::TEXT as routes, -- Append new route id
          fc.hops + 1 AS hops -- Increase number of hops (depth)
        FROM route AS r, flight_chain AS fc -- Circullar join
        WHERE       
          r.source_city_mapping = fc.destination_city_mapping -- Join on current destination mapping = next source mapping
          AND (r.source_city_mapping <> ALL(fc.sources)) -- Avoid circular paths
          and (r.destination_city_mapping <> ALL(fc.destinations)) -- Avoid circular paths
          AND fc.hops < :maxHops -- Stop if hops (deph) is exceeded
)      
SELECT routes AS routes, 
    total_price AS totalPrice, 
    hops AS hops, 
    destinations AS destinations 
FROM flight_chain
WHERE :destinationAirportMapping = destinations[array_upper(destinations, 1)] -- Return only routes ending with destination mapping
ORDER BY totalPrice ASC -- Order by total price descending
LIMIT :maxLimit; -- Limit results by some desired number
```

After the results are returned by the given SQL query the data object has the given structure:

```
public class CheapestFlightChain {

    private String routes;

    private Float totalPrice;

    private Integer hops;
}
```

Hibernate unfortunately doesn't support ARRAY of TEXT mapping, so routes are mapped as string and then split later.

The routes are a list of routeIds, based upon what the routes in the chain are retreived from db, the response is mapped
and is presented to the user in the given json response:

### Request JSON

```
GET /flights/cheapest
----
BODY
----
{
    "source": {
        "id": "sourceCityId", -- Either source city id must be provider or name+country
        "name": "sourceCityName",
        "country": "sourceCityCountryName"
    },
    "destination": {
       "id": "destinationCityId", -- Either destination city id must be provider or name+country
       "name": "destinationCityName",
       "country": "destinationCityCountryName"
    },
    "maxHops": maxHops,       -- Optional, will use default if null
    "maxResults": maxResults  -- Optional, will use default if null
}
```


### Response JSON

```
{
    "chains": [
        {
            "stops": [
                {
                    "embark": {
                        "city": "sourceCityName",
                        "country": "sourceCityCountryName",
                        "airport": "sourceCityAirportName",
                        "airportCode": "sourceCityAirportCode"
                    },
                    "disembark": {
                        "city": "destinationCityName",
                        "country": "destinationCityCountryName",
                        "airport": "destinationCityAirportName",
                        "airportCode": "destinationCityAirportCode"
                    },
                    "airlineCode": "airlineCode",
                    "equipment": "equipment"
                }
            ],
            "totalPrice": totalPrice, -- Total price of this chain
            "hops": hops -- Hops in this chain
        }
    ],
    "found": found -- Found route chains
}
```

The results are ordered by the cheapest ascending.

## Developer comments

* The airports csv is badly formatted, not following the csv quote convention and is using \"
instead of "" when quoting inside encapsulated token (https://en.wikipedia.org/wiki/Comma-separated_values#General_functionality), causing apache commons csv to fail with: java.io.IOException: (line 329) invalid char between encapsulated token and delimiter
* Please use the provided CSVs in the /import folder that have this corrected.
Naming of the given airport.txt and route.txt has also been changed to airport.csv and route.csv since I added validation to importer.
* Removed salt from user and used basic BCryptPasswordEncoder to save time
* Used simple spring security basic auth, username & password sent with each request to save time
* Use predefined users, admin/admin and user/user for convenience
* Longitude and latitude in airport considered strings since they have no logical value in implementation
* Code written with the highest emphasis to readability and maintainability since it is a challenge that is going to be reviewed by a person :)
* I have tried to achieve at least happy case unit test coverage, but due to time constraints it is not as detailed and in depth as I usually prefer.
* Integration tests covering most critical and important scenarios, again due to time constraints

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

* Besides, comment’s description, each comment should have created and modified date.

### API Access

* ADMIN - No restrictions
* USER - Comments and flight search

### Data structure & description

#### User

Every user has: first name, last name, user name, password and salt.
The regular user has to register in order to be able to use a Flight advisor.

* id - INTEGER (autoincrement) - 0
* firstName - VARCHAR - "John"
* lastName - VARCHAR - "Doe"
* userName - VARCHAR - "john.doe"
* password - VARCHAR - "encrypted"

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

* id - VARCHAR(36) (UUID)
* externalId - BIGINT
* name - VARCHAR - "Goroka Airport"
* cityId -  VARCHAR(36)
* iata - VARCHAR(3) NULLABLE - "GKA"
* icao - VARCHAR(4) NULLABLE - "AYGA"
* latitude - VARCHAR(64) - "-6.081689834590001"
* longitude - VARCHAR(64) - "145.391998291"
* altitude - FLOAT - "5282"
* timeZone - INTEGER - "10"
* dst - VARCHAR(1) - "U" NOT NULL
* tz - VARCHAR(64) - "Pacific/Port_Moresby"
* type - VARCHAR(64), ENUM - "airport", "unknown"
* source - VARCHAR - "OurAirports"

#### Airline

* id - VARCHAR(36)
* externalId - INTEGER (autoincrement) - 0
* code - VARCHAR(4) - "AYGA"

#### Route

Airline - 2-letter (IATA) or 3-letter (ICAO) code of the airline.
Airline ID - Identifier for airline.
Source airport - 3-letter (IATA) or 4-letter (ICAO) code of the source airport.
Source airport ID - Identifier for source airport.
Destination airport - 3-letter (IATA) or 4-letter (ICAO) code of the destination airport.
Destination airport ID - Unique OpenFlights identifier for destination airport.
Codeshare - "Y" if this flight is a code share, empty otherwise.
Stops - Number of stops on this flight ("0" for direct).
Equipment - 3-letter codes for plane type(s) generally used on this flight, separated by spaces.
Price - Flight cost.

* id - INTEGER (autoincrement) - 0
* airlineCode - VARCHAR(4) - "2B"
* airlineId -  VARCHAR(36)
* sourceAirportCode - VARCHAR(4) - "AER"
* sourceAirportId -  VARCHAR(36)
* destinationAirportCode - VARCHAR(4) - "KZN"
* destinationAirportId - VARCHAR(36)
* codeShare - BOOLEAN - false
* stops - INTEGER, DEFAULT 0 - "0"
* equipment - TEXT - "CR2 CR3"
* price - NUMBER - "95.87"

### REST API

Rest API documentation is available VIA swagger.

`[HOST]/swagger-ui.html`

e.g. `http://localhost:8080/swagger-ui.html`

# Last modified: 08.11.2020
