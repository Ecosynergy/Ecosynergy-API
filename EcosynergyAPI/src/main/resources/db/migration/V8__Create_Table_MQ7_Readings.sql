CREATE TABLE IF NOT EXISTS mq7_readings(
    id bigint(20) primary key auto_increment,
    value float not null,
    date timestamp not null
);