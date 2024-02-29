const Mq135Sensor = require("../models/Mq135Sensor");
const status = require("http-status");

class Mq135SensorController{
    async index(req, res){
        try{
            const readings = await Mq135Sensor.findAll();
            res.json(readings);
        } catch(err){
            console.error(err);
            res.sendStatus(status.INTERNAL_SERVER_ERROR);
        }
    }

    async create(req, res){
        try{
            const mq135Value = req.body.mq135Value;
            if(!isNaN(mq135Value) && mq135Value){
                const response = await Mq135Sensor.create({value: mq135Value, date: new Date()});
                if(response[0] > 0){
                    res.status(status.CREATED).json(await Mq135Sensor.findById(response[0]));
                }
            } else {
                res.status(status.BAD_REQUEST).json({error: "Missing fields value"});
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
                res.status(status.BAD_REQUEST).json({error: "Invalid field"});
            } else {
                const reading = await Mq135Sensor.findById(id);
                if(reading){
                    res.json(reading);
                } else {
                    res.status(status.NOT_FOUND).json({error: "No reading found"})
                }
            }
        } catch(err){
            console.error(err);
            res.sendStatus(status.INTERNAL_SERVER_ERROR);
        }
    }
}

module.exports = new Mq135SensorController();