RestfulAssignment
=====================

Simple REST-based API that processes requests to manage an "events" resource over HTTP using JSON.

Development environment
------------
Java/J2EE 

Used Spring Framework to simplify the developement

Web Container: Tomcat

Database: SQLite

Installation
------------

1. Install Tomcat 7 and setup the server to listen 8999
2. Deploy Assignment.war in webapps folder of Tomcat
3. Configure the database location in db.properties (/<TOMCAT_HOME>Assignment/WEB-INF/classes/resources/db.properties)
4. If you change the db.properties then you need to restart the server.

Details
------------
1. Codes.java holds the status codes for rest calls
2. Queries.java holds all the quries of the project
3. RestfulProcessor.java is the controller which is responsible to process all the events requests


API Calls for processesing the event requests
------------

**Insert the new event:**

curl -X POST "http://localhost:8080/Assignment/events" -H "Content-Type: application/json" -d '{"data":"NAME is now at LATITUDE/LONGITUDE"}'

```json
{
  "status_code": 200,
  "description": "Event processed.",
  "event_info": {
    "id": 1,
    "data": "NAME is now at LATITUDE/LONGITUDE"
  }
  
}
```
------------

**Get all the events:**

curl "http://localhost:8080/Assignment/events"
```json
{
  "status_code": 200,
  "event_info": [
    {
      "id": 1,
      "data": "NAME is now at LATITUDE/LONGITUDE"
    },
    {
      "id": 2,
      "data": "NAME is now at LATITUDE100/LONGITUDE100"
    }
  ],
  "description": "Request processed."
}
```
------------
**Delete all the events:**
```json
curl -X DELETE "http://localhost:8080/Assignment/events"

{
  "status_code": 200,
  "description": "All events got deleted."
}
```
------------
**Get particular event:**

curl "http://localhost:8080/Assignment/events/2"
```json
{
  "status_code": 200,
  "event_info": {
    "id": 2,
    "data": "NAME is now at LATITUDE100/LONGITUDE100"
  },
  "description": "Request processed."
}
```
------------
**Updating the existing event with different data:**

curl -X PUT "http://localhost:8080/Assignment/events/2" -H "Content-Type: application/json" -d '{"data":"NAME is now at LATITUDE NEW/LONGITUDE NEW"}'
```json

{
  "status_code": 200,
  "event_info": {
    "id": 2,
    "data": "NAME is now at LATITUDE NEW/LONGITUDE NEW"
  },
  "description": "Request processed."
}
```
------------
**Delete the particular event**

curl -X DELETE "http://localhost:8080/Assignment/events/2"
```json
{
  "status_code": 200,
  "description": "Event deleted successfully."
}
```
------------

Few error messages
------------
**When the data is not a proper JSON**
```json
{
  "status_code": 505,
  "description": "Invalid JSON payLoad."
}
```
------------

**When the resource id is invalid during update/select/delete**
```json
{
  "status_code": 506,
  "description": "Invalid resource id."
}
```
------------

**When no data attribute found in the event payLoad**
```json
{
  "status_code": 501,
  "description": "No data found in the event payLoad."
}
```
------------




