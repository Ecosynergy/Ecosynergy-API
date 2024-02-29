const User = require("../models/User");
const PasswordToken = require("../models/PasswordToken");
const status = require("http-status");

class UserController{
    async index(req, res){
        try{
            res.status(status.OK).json(await User.findAll());
        } catch(err){
            console.log(err);
            res.sendStatus(status.INTERNAL_SERVER_ERROR);
        }
    }

    async findUser(req, res){
        try{
            var id =  req.params.id;
            if(isNaN(id)){
                res.status(status.BAD_REQUEST).json({ error: "Invalid user ID" });
            } else {
                let user = await User.findById(id);
                if(!user){
                    res.status(404).json({ error: "No users found" });
                } else {
                    res.status(status.OK).json(user);
                }
            }
        } catch(err){
            res.status(status.INTERNAL_SERVER_ERROR).json({ error: err });
        }
    }
    async create(req, res){
        const {name, email, password, role} = req.body;
        var user = email.substring(0, email.indexOf("@"));
        var domain = email.substring(email.indexOf("@" )+ 1, email.length);

        if(!name || !email || !password || !role){
            res.status(status.BAD_REQUEST).json({ error: "Missing data" });
        } else if(!((user.length >=1) &&
                (domain.length >=3) &&
                (user.search("@")==-1) &&
                (domain.search("@")==-1) &&
                (user.search(" ")==-1) &&
                (domain.search(" ")==-1) &&
                (domain.search(".")!=-1) &&
                (domain.indexOf(".") >=1)&&
                (domain.lastIndexOf(".") < domain.length - 1)))
        {
            res.status(status.BAD_REQUEST).json({ error: "Invalid E-mail" });
        } else if(isNaN(role)){
            res.status(status.BAD_REQUEST).json({ error: "Role must be a number" })
        } else {
            try{
                let userByEmail = await User.findByEmail(email);
                if(userByEmail){
                    res.status(409).json({ error: "User already exists" });
                } else {
                    await User.create({
                        name: name,
                        email: email,
                        password: password,
                        role: role
                    });
                    res.sendStatus(201);
                }
            } catch(err){
                console.log(err);
                res.status(status.INTERNAL_SERVER_ERROR).json({ error: err });
            }
        }
    }

    async edit(req,res){
        const id = req.params.id;
        const {name, email, role} = req.body;
        try{
            let result = await User.update({ id, name, email, role });
            if(result.status){
                req.loggedUser.email = email;
                res.sendStatus(status.OK);
            } else {
                res.status(result.statusCode).json({ error: result.error });
            }
        } catch(err){
            console.log(err);
            res.status(status.INTERNAL_SERVER_ERROR).json({ error: err });
        }
    }

    async remove(req, res){
        const id = req.params.id;
        try{
            if(!isNaN(id)){
                let result = await User.delete(id);
                if(result.status){
                    res.sendStatus(status.OK);
                } else {
                    res.status(result.statusCode).json({ error: result.error });
                }
            } else {
                res.status(status.BAD_REQUEST).json({ error: 'Invalid user ID' })
            }
        } catch(err){
            console.log(err);
            res.status(status.INTERNAL_SERVER_ERROR).json({ error: err });
        }
    }

    async recoverPassword(req, res){
        var email = req.body.email;
        try{
            if(email){
                let result = await PasswordToken.create(email);
                if(result.status){
                    res.status(status.OK).json({ token: result.token });
                } else {
                    res.status(result.statusCode).json({ error: result.error });
                }
            } else {
                res.status(status.BAD_REQUEST).json({ error : "Invalid email" });
            }
        } catch(err){
            console.log(err);
            res.status(status.INTERNAL_SERVER_ERROR).json({error: err});
        }
    }

    async changePassword(req, res){
        const {token, password} = req.body;
        try{
            const validateToken = await PasswordToken.validate(token);
            if (validateToken.status) {
                let result = await User.changePassword({id: validateToken.token.user_id, password, token: validateToken.token.token});
                if(result.status){
                    res.sendStatus(status.OK);
                } else {
                    res.status(result.statusCode).json({error: result.error});
                }
            } else {
                res.status(validateToken.statusCode).json({error: validateToken.error ? validateToken.error : "Token already used"});
            }
        } catch(err){
            console.error(err);
            res.status(status.INTERNAL_SERVER_ERROR).json({error: err});
        }
        
    }

    async login(req, res){
        var {email, password} = req.body;
        let user = email.substring(0, email.indexOf("@"));
        let domain = email.substring(email.indexOf("@" )+ 1, email.length);
        try{
            if( !email || !password ){
                res.status(status.BAD_REQUEST).json({ error: "Missing data" });
            } else if(!((user.length >=1) &&
                    (domain.length >=3) &&
                    (user.search("@")==-1) &&
                    (domain.search("@")==-1) &&
                    (user.search(" ")==-1) &&
                    (domain.search(" ")==-1) &&
                    (domain.search(".")!=-1) &&
                    (domain.indexOf(".") >=1)&&
                    (domain.lastIndexOf(".") < domain.length - 1)))
            {
                res.status(status.BAD_REQUEST).json({ error: "Invalid E-mail" });
            } else {
                const user = await User.findByEmail(email);
                if(!user){
                    res.status(status.NOT_FOUND).json({error: "User not found"});
                } else if(await User.validatePassword(password, user.password)){
                    res.status(status.OK).json({ token : await PasswordToken.createLoginToken(user)});
                } else {
                    res.status(status.UNAUTHORIZED).json({error: "Wrong Password"});
                }
            }
        } catch(err){
            console.error(err);
            res.status(status.INTERNAL_SERVER_ERROR).json({error: err});
        }
    }

    async validate(req, res){
        res.sendStatus(status.OK);
    }
}

module.exports = new UserController();