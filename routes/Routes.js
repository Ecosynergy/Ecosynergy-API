const express = require('express');
const Router = express.Router();

// Controllers
const FireSensorController = require("../controllers/FireSensorController");
const Mq7SensorController = require("../controllers/Mq7SensorController");
const Mq135SensorController = require("../controllers/Mq135SensorController");
const UserController = require("../controllers/UserController");

// Routes
Router.get('/', (req, res) => res.json({message: "Hello!!!"}));
Router.get("/firereadings", FireSensorController.index);

module.exports = Router;