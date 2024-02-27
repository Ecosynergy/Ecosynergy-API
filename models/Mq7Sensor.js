const knex = require("../database/connection");
const status = require("http-status");

class Mq7Sensor {
    constructor(){
        this.table = "mq7readings"
    }

    async findAll (){
        try{
            return await knex.select().table(this.table);
        } catch(err){
            throw err;
        }
    }

    async findById(id){
        try{
            const reading = knex.select().where({id}).first().table(this.table);

            if(reading){
                return reading;
            } else {
                return null;
            }
        } catch(err){
            throw err;
        }
    }

    async create(value){
        try{
            return await knex.insert({
                value: value,
                date: new Date()
            });
        } catch(err){
            throw err;
        }
    }
}

module.exports = new Mq7Sensor();