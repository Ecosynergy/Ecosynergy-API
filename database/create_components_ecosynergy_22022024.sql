create database ecosynergy;
use ecosynergy;

create table users(
id int primary key auto_increment,
name varchar(255) not null,
email varchar(255) unique not null,
password varchar(255) not null
);

create table mq7readings(
id int primary key auto_increment,
value float not null,
date datetime not null
);

create table mq135readings(
id int primary key auto_increment,
value float not null,
date datetime not null
);

create table firereadings(
id int primary key auto_increment,
isFire boolean not null,
date datetime not null
);

CREATE TABLE `passwordtokens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `user_id` int NOT NULL,
  `used` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `passwordTokens` (`user_id`),
  CONSTRAINT `passwordtokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);