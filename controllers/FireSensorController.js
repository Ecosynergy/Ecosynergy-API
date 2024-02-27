const knex = require("../database/connection");
const status = require("http-status");

// Model
const FireSensor = require("../models/FireSensor");

class FireSensorController{
    async index(req, res){
        try{
            res.json(await FireSensor.findAll());
        } catch(err){
            console.error(err);
            res.sendStatus(status.INTERNAL_SERVER_ERROR);
        }
    }
}

module.exports = new FireSensorController();