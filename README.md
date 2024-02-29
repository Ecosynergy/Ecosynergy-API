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
*Endpoint:* *GET* `/firereadings`

##### Responses
###### OK 200
If this response is returned, you will receive a list of all Fire readings. Response example:
```
[
    {
        "id": 1,
        "isFire": 1,
        "date": "2024-02-28T14:50:52.000Z"
    },
    {
        "id": 2,
        "isFire": 1,
        "date": "2024-02-28T23:42:07.000Z"
    }
]
```
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Internal Server Error 500
Indicates that an internal error has occurred. Reasons: Processing error or unhandled exceptions.

#### Search Reading
*Endpoint:* GET /firereading/:id

##### URL Parameters
**`id` (int):** The unique identifier of the sensor reading

##### Responses
###### OK 200
If this response is returned, you will receive a fire reading with the same ID passed as a parameter. Response example:
```
{
    "id": 1,
    "isFire": 1,
    "date": "2024-02-28T14:50:52.000Z"
}
```
###### Bad Request 400
If this response is returned, it means that the parameter is not valid. *Reasons:* ID passed as a parameter is not a number. Response example:
```
{
    "error": "Invalid ID"
}
```
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Not Found 404
If this response is returned, it means the fire reading was not found. Response example:
```
{
    "error": "No reading found"
}
```
###### Internal Server Error 500
Indicates that an internal error has occurred. *Reasons:* Processing error or unhandled exceptions.

#### Create Reading
*Endpoint:* POST /firereading

##### Request Parameters:
**isFire:** Boolean value to indicate whether the sensor is returning true or false
```
{
    "isFire": `[0 or 1] or [true or false]`
}
```

##### Responses
###### CREATED 201
If this response is returned, it means the game has been created. A JSON will be returned containing the reading you registered. Response example:
```
{
    "id": 3,
    "isFire": 0,
    "date": "2024-02-29T00:31:15.000Z"
}
```
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Internal Server Error 500
Indicates that an internal error has occurred. *Reasons:* Processing error or unhandled exceptions.

### MQ7 Sensor

#### List Readings
*Endpoint:* GET /mq7readings

##### Responses
###### OK 200
If this response is returned, you will receive a list of all MQ7 readings. Response example:
```
[
    {
        "id": 1,
        "value": 4568,
        "date": "2024-02-28T14:48:57.000Z"
    },
    {
        "id": 2,
        "value": 4568,
        "date": "2024-02-28T23:39:27.000Z"
    }
]
```
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Internal Server Error 500
Indicates that an internal error has occurred. *Reasons:* Processing error or unhandled exceptions.

#### Search Reading
*Endpoint:* GET /mq7reading/:id

##### URL Parameters:
*`id` (number):* The unique identifier of the sensor reading

##### Responses
###### OK 200

### MQ135 Sensor
