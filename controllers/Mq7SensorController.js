const MQ7Sensor = require("../models/Mq7Sensor");
const status = require("http-status");

class Mq7SensorController{
    async index(req, res){
        try{
            const readings = await MQ7Sensor.findAll();
            res.json(readings);
        } catch(err){
            console.error(err);
            res.sendStatus(status.INTERNAL_SERVER_ERROR);
        }
    }

    async create(req, res){
        try{
            const mq7Value = req.body.mq7Value;

            if(!isNaN(mq7Value)){
                const response = await MQ7Sensor.create({value: mq7Value, date: new Date()});
                if(response[0] > 0){
                    res.status(status.CREATED).json(await MQ7Sensor.findById(response[0]));
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
            if(isNaN(id)){
                res.status(status.BAD_REQUEST).json({error: "Invalid field"});
            } else {
                const reading = await MQ7Sensor.findById(id);
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

module.exports = new Mq7SensorController();