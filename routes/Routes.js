const express = require('express');
const Router = express.Router();

// Controllers
const FireSensorController = require("../controllers/FireSensorController");
const Mq7SensorController = require("../controllers/Mq7SensorController");
const Mq135SensorController = require("../controllers/Mq135SensorController");
const UserController = require("../controllers/UserController");

// Index - Routes
Router.get('/', (req, res) => res.json({message: "Hello!!!"}));

// Fire Sensor - Routes
Router.get("/firereadings", FireSensorController.index);
Router.get("/firereading/:id", FireSensorController.findById);
Router.post("/firereading", FireSensorController.create);

// Mq7 Sensor - Routes
Router.get("/mq7readings", Mq7SensorController.index);
Router.get("/mq7reading/:id", Mq7SensorController.findById);
Router.post("/mq7reading", Mq7SensorController.create);

module.exports = Router;