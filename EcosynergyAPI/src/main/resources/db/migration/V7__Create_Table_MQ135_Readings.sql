CREATE TABLE IF NOT EXISTS mq135_readings(
    id bigint(20) primary key auto_increment,
    value float not null,
    date timestamp not null
);