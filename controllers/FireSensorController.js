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

    async create(req, res){
        try{
            const isFire = req.body.isFire;
            if(!isFire){
                res.status(status.BAD_REQUEST).json({error: "Missing field value"});
            } else {
                const response = await FireSensor.create({isFire, date: new Date()});
                res.status(status.CREATED).json(response);
            }
        } catch(err){
            console.error(err);
            res.sendStatus(status.INTERNAL_SERVER_ERROR);
        }
    }
}

module.exports = new FireSensorController();