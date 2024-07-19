# Ecosynergy API
Welcome to the documentation for the Ecosynergy API, a TCC (Final Coursework) project focused on managing gas emissions with sensors. This API provides functionalities to perform and retrieve readings from MQ7, MQ135, and Fire sensors. Additionally, it offers features for user management, JWT token authentication, and login functionality.
## Table of Contents
1. Authentication
2. Sensors
	- Fire Sensor
	- MQ7 Sensor
 	- MQ135 Sensor 	
3. Users
	- List Users
	- Search User
 	- Create User
	- Edit User
 	- Recover Password
  	- Change Password
	- Login

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
*Endpoint:* GET `/firereading/:id`

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
*Endpoint:* POST `/firereading`

##### Request Parameters:
**isFire:** Boolean value to indicate whether the sensor is returning true or false
```
{
    "isFire": `[0 or 1] or [true or false]`
}
```

##### Responses
###### CREATED 201
If this response is returned, it means the reading has been created. A JSON will be returned containing the reading you registered. Response example:
```
{
    "id": 3,
    "isFire": 0,
    "date": "2024-02-29T00:31:15.000Z"
}
```
###### Bad Request 400
If this response is returned, it means the parameters are not valid. *Reasons:* Missing required fields or invalid data type.
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Internal Server Error 500
Indicates that an internal error has occurred. *Reasons:* Processing error or unhandled exceptions.

### MQ7 Sensor

#### List Readings
*Endpoint:* GET `/mq7readings`

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
*Endpoint:* GET `/mq7reading/:id`

##### URL Parameters:
*`id` (number):* The unique identifier of the sensor reading

##### Responses
###### OK 200
If this response is returned, you will receive a MQ7 reading with the same ID passed as a parameter. Response example:
```
{
    "id": 1,
    "value": 4568,
    "date": "2024-02-28T14:48:57.000Z"
}
```
###### Bad Request 400
If this response is returned, it means that the parameter is not valid. *Reasons:* ID passed as a parameter is not a number or not passed. Response example:
```
{
    "error": "Invalid ID"
}
```
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Not Found 404
If this response is returned, it means the MQ7 reading was not found. Response example:
```
{
    "error": "No reading found"
}
```
###### Internal Server Error 500
Indicates that an internal error has occurred. *Reasons:* Processing error or unhandled exceptions.

#### Create Reading
*Endpoint:* POST `/mq7reading`

##### Request Parameters:
**mq7Value:** The value of the MQ7 reading.
```
{
    "mq7Value": [number]
}
```

##### Responses
###### CREATED 201
If this response is returned, it means the reading has been created. A JSON will be returned containing the reading you registered. Response example:
```
{
    "id": 3,
    "value": 4568,
    "date": "2024-02-29T00:44:56.000Z"
}
```
###### Bad Request 400
If this response is returned, it means the parameters are not valid. *Reasons:* Missing required fields or invalid data type.
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Internal Server Error 500
Indicates that an internal error has occurred. *Reasons:* Processing error or unhandled exceptions.

### MQ135 Sensor

#### List Readings
*Endpoint:* GET `/mq135readings`

##### Responses
###### OK 200
If this response is returned, you will receive a list of all MQ135 readings. Response example:
```
[
    {
        "id": 1,
        "value": 4565,
        "date": "2024-02-28T14:30:37.000Z"
    },
    {
        "id": 2,
        "value": 4565,
        "date": "2024-02-28T14:33:17.000Z"
    },
    {
        "id": 3,
        "value": 4565,
        "date": "2024-02-28T14:33:58.000Z"
    },
    {
        "id": 4,
        "value": 4565,
        "date": "2024-02-28T14:36:07.000Z"
    }
]
```
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Internal Server Error 500
Indicates that an internal error has occurred. *Reasons:* Processing error or unhandled exceptions.

#### Search Reading
*Endpoint:* GET `/mq135reading/:id`

##### URL Parameters:
*`id` (number):* The unique identifier of the sensor reading

##### Responses
###### OK 200
If this response is returned, you will receive a MQ135 reading with the same ID passed as a parameter. Response example:
```
{
    "id": 1,
    "value": 4565,
    "date": "2024-02-28T14:30:37.000Z"
}
```
###### Bad Request 400
If this response is returned, it means that the parameter is not valid. *Reasons:* ID passed as a parameter is not a number or not passed. Response example:
```
{
    "error": "Invalid ID"
}
```
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Not Found 404
If this response is returned, it means the MQ135 reading was not found. Response example:
```
{
    "error": "No reading found"
}
```
###### Internal Server Error 500
Indicates that an internal error has occurred. *Reasons:* Processing error or unhandled exceptions.

#### Create Reading
*Endpoint:* POST `/mq135reading`

##### Request Parameters:
**mq135Value:** The value of the MQ135 reading.
```
{
    "mq135Value": [number]
}
```

##### Responses
###### CREATED 201
If this response is returned, it means the reading has been created. A JSON will be returned containing the reading you registered. Response example:
```
{
    "id": 8,
    "value": 4567,
    "date": "2024-02-29T00:56:15.000Z"
}
```
###### Bad Request 400
If this response is returned, it means the parameters are not valid. *Reasons:* Missing required fields or invalid data type.
###### Unauthorized 401
If this response is returned, it means that something failed during the request authentication process. *Reasons:* Invalid token or expired token.
###### Internal Server Error 500
Indicates that an internal error has occurred. *Reasons:* Processing error or unhandled exceptions.
