create table users(
                                    id bigint primary key auto_increment,
                                    name varchar(255) not null,
                                    email varchar(255) unique not null,
                                    password varchar(255) not null,
                                    gender varchar(255) not null,
                                    nationality varchar(255)
);