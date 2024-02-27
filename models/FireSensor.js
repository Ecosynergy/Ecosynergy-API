const knex = require("../database/connection");
const status = require("http-status");

class FireSensor{
    constructor(){
        this.table = "firereadings"
    }
    async findAll(){
        try{
            return await knex.select().table(this.table);
        }catch(err){
            throw err;
        }
    }

    async create(reading){
        try{
            return await knex.insert({
                isFire: reading.isFire,
                date: reading.date
            }).table(this.table);
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
}

module.exports = new FireSensor();