create table if not exsits Account (
    id varchar(100) not null primary key,
    username varchar(100) not null,
    password varchar(100) not null,
    phone_number varchar(100) not null
);

create table if not exists Appointment (
    id identity,
    subject varchar(200) not null,
    content TEXT not null,
    longitude float not null,
    latitude float not null,
    location_name varchar(200) not null,
    created_at timestamp not null,
    start_at Date not null,
    end_at Date not null
);

create table if not exists Account_Appointment (
    account varchar(100) not null,
    appointment bigint not null
);

alter table Account_Appointment
    add foreign key (account) references Account(id);
alter table Account_Appointment
    add foreign key (appointment) references Appointment(id);

/*
create table if not exists Caching (
    longitude float not null,
    latitude float not null,
    location_name varchar(200) not null
);
*/