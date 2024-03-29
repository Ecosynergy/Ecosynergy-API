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

// Mq135 Sensor - Routes
Router.get("/mq135readings", Mq135SensorController.index);
Router.get("/mq135reading/:id", Mq135SensorController.findById);
Router.post("/mq135reading", Mq135SensorController.create);

// User - Routes
Router.get("/users", UserController.index);
Router.get("/user/:id", UserController.findUser);
Router.post("/user", UserController.create);
Router.put("/user/:id", UserController.edit);
Router.delete("/user/:id", UserController.remove);
Router.post("/recoverpassword", UserController.recoverPassword);
Router.post("/changepassword", UserController.changePassword);
Router.post("/login", UserController.login);
Router.post("/validate", UserController.validate);

module.exports = Router;