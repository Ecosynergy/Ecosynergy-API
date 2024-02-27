const status = require("http-status");

class Mq135Sensor{
    constructor(){
        this.table = "mq135readings";
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
                isFire: reading.value,
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

module.exports = new Mq135Sensor();