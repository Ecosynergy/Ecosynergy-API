# Ecosynergy API
Welcome to the documentation for the Ecosynergy API, a TCC (Final Coursework) project focused on managing gas emissions with sensors. This API provides functionalities to perform and retrieve readings from MQ7, MQ135, and Fire sensors. Additionally, it offers features for user management, JWT token authentication, and login functionality.
## Table of Contents
1. Authentication
2. Sensors
	- List Readings
	- Record Reading
3. Users
	- Register User
	- Login
	- Update Profile

## Authentication
## Sensors
### Fire Sensor
#### List Readings
**Endpoint:** GET /firereadings
#### Search Reading
**Endpoint:** GET /firereading/:id
##### URL Parameters
**`id` (int):** The unique identifier of the sensor reading
#### Create Reading
**Endpoint:** POST /firereading
##### Request Parameters:
**isFire:** Boolean value to indicate whether the sensor is returning true or false
```
{
    "isFire": `[0 or 1] or [true or false]`
}
```
### MQ7 Sensor
#### List Readings
**Endpoint:** GET /mq7readings
#### Search Reading
**Endpoint:** GET /mq7reading/:id
##### URL Parameters:
**`id` (int):** The unique identifier of the sensor reading
##### Responses
###### OK 200

### MQ135 Sensor
