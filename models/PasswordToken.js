const knex = require("../database/connection");
const User = require("./User");
const status = require("http-status");

// Json Web Token
const JWT = require("jsonwebtoken");
const JWT_SECRET = "anderson-rod-dev";

class PasswordToken{
    constructor(){
        this.table = "passwordtokens";
    }
    async create(email) {
        try {
            const user = await User.findByEmail(email);

            if (!user) {
                return { status: false, statusCode: status.NOT_FOUND, error: "User not found" };
            }

            var tokens = await knex.select().where({user_id: user.id}).table(this.table);
            var isUsed = true;
            tokens.forEach((token) => {
                if(!token.used)
                    isUsed = false
            });
            if(!isUsed){
                return {status: false, statusCode: status.CONFLICT, error: "There is already a active token for this email."};
            }
            const generatedToken = await new Promise((resolve, reject) => {
                JWT.sign({ id: user.id }, JWT_SECRET, { expiresIn: "24h" }, (err, token) => {
                    if (err) {
                        reject(err);
                    }
                    resolve(token);
                });
            });

            await knex.transaction(async (trans) => {
                await knex.insert({
                    user_id: user.id,
                    used: false,
                    token: generatedToken
                }).table(this.table);
            });

            return { status: true, token: generatedToken };
        } catch (err) {
            console.error(err);
            throw new Error("Failed to create password token");
        }
    }

    async validate(token){
        try{
            var tk = await knex.select().where({token: token}).first().table(this.table);
            if(tk){
                return {status: !tk.used, statusCode: tk.used ? status.UNAUTHORIZED : status.OK ,token: tk};
            } else {
                return {status: false, statusCode: status.UNAUTHORIZED, error: "Expired or invalid token"};
            }
        } catch(err){
            throw err;
        }

    }

    async createLoginToken(user){
        return await new Promise((resolve, reject) => {
            JWT.sign({ id: user.id}, JWT_SECRET, { expiresIn: "24h" }, (err, token) => {
                if (err) {
                    reject(err);
                }
                resolve(token);
            });
        });
    }

    async setUsed(token){
        try{
            await knex.update({ used:  true }).where({ token: token}).table(this.table);
        } catch(err){
            throw err;
        }
    }
}

module.exports = new PasswordToken();