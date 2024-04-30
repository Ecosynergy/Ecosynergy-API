create table fire_readings(
    id bigint(60) primary key auto_increment,
    is_fire boolean not null,
    date timestamp not null
);