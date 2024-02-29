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
            if(isFire == undefined || isFire == null){
                res.status(status.BAD_REQUEST).json({error: "Missing field value"});
            } else {
                const response = await FireSensor.create({isFire, date: new Date()});
                if(response[0] > 0){
                    res.status(status.CREATED).json(await FireSensor.findById(response[0]));
                }
            }
        } catch(err){
            console.error(err);
            res.sendStatus(status.INTERNAL_SERVER_ERROR);
        }
    }

    async findById(req, res){
        try{
            const id = req.params.id;
            if(isNaN(id) || !id){
                res.status(status.BAD_REQUEST).json({error: "Invalid ID"});
            } else {
                const reading = await FireSensor.findById(id);
                console.log(reading);
                if(!reading){
                    res.status(status.NOT_FOUND).json({error: "No readings found"})
                } else {
                    res.json(reading);
                }
            }
        } catch(err){
            console.error(err);
            res.sendStatus(status.INTERNAL_SERVER_ERROR);
        }
    }
}

module.exports = new FireSensorController();