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
            const reading = await knex.select().where({id}).first().table(this.table);
            return reading;
        } catch(err){
            throw err;
        }
    }

    async create(reading){
        try{
            return await knex.insert({
                value: reading.value,
                date: reading.date
            }).table(this.table);
        } catch(err){
            throw err;
        }
    }
}

module.exports = new Mq7Sensor();