const knex = require("../database/connection");
const bcrypt = require("bcryptjs");
const status = require("http-status");

// Json Web Token
const JWT = require("jsonwebtoken");
const JWT_SECRET = "anderson-rod-dev";

class User{
    constructor(){
        this.table = "users";
    }
    async create(user){
        try{
            const salt = bcrypt.genSaltSync(10);
            const hashPassword = bcrypt.hashSync(user.password, salt);

            await knex.insert({
                name: user.name,
                email: user.email,
                password: hashPassword,
                role: user.role
            }).table(this.table);
        } catch(err){
            throw err;
        }
    }

    async findAll(){
        try{
            return await knex.select(["users.id", "users.name as name", "users.email as email", "roles.name as role"])
                            .table(this.table)                
                            .innerJoin("roles", "roles.id", "users.role");
        } catch(err){
            throw err;
        }
    }

    async findById(id){
        try{
            return await knex.select(["users.id", "users.name as name", "users.email as email", "roles.name as role"])
                            .where({ "users.id": id })
                            .first()
                            .table(this.table)                
                            .innerJoin("roles", "roles.id", "users.role");
        } catch(err){
            throw err;
        }
    }

    async findByEmail(email){
        try{
            return await knex.select(["users.id", "users.name as name", "users.email as email", "roles.name as role", "users.password"])
                            .where({ email: email })
                            .first()
                            .table(this.table)                
                            .innerJoin("roles", "roles.id", "users.role");
        } catch(err){
            throw err;
        }
    }

    async update(user){
        try{
            var existingUser = await this.findById(user.id);
            if(existingUser){
                if(user.email != existingUser.email){
                    let result = await this.findByEmail(user.email);
                    if(result){
                        return {status: false, statusCode: status.CONFLICT, error: "This email already belongs to a user"}
                    }
                }

                if(!user.name || !user.role){
                    return {status: false, statusCode: status.BAD_REQUEST, error: "Missing parameters"};
                }

                await knex.update({
                    name: user.name,
                    email: user.email,
                    role: user.role
                }).where({id: user.id}).table(this.table);
                return {status: true};
            } else {
                return {status: false, statusCode: status.NOT_FOUND, error: "User not found"};
            }
        } catch(err){
            throw err;
        }
    }

    async delete(id){
        try {
            var user = await this.findById(id);
            if(user) {
                await knex.delete().where({id: id}).table(this.table);
                return {status: true};
            } else {
                return {status: false, statusCode: status.NOT_FOUND, error: "User not found"};
            }
        } catch(err){
            throw err;
        }
    }

    async changePassword(data){
        try{
            const salt = bcrypt.genSaltSync(10);
            const newHashPassword = bcrypt.hashSync(data.password, salt);
            await knex.update({password: newHashPassword}).where({id: data.id}).table(this.table);
            await this.setUsed(data.token);
            return {status: true};
        } catch(err){
            throw err;
        }
    }

    async setUsed(token){
        try{
            await knex.update({ used:  true }).where({ token: token}).table("passwordtokens") ;
        } catch(err){
            throw err;
        }
    }

    async validatePassword(password, userPassword){
        return bcrypt.compareSync(password, userPassword);
    }

    async validateToken(token) {
        try {
            const data = await new Promise((resolve, reject) => {
                JWT.verify(token, JWT_SECRET, (err, data) => {
                    if (err) {
                        reject(err);
                    } else {
                        resolve(data);
                    }
                });
            });
    
            const user = await this.findById(data.id);
    
            return { status: true, user: user };
        } catch (err) {
            return { status: false, statusCode: status.FORBIDDEN, error: "Invalid Token" };
        }
    }
}

module.exports = new User();