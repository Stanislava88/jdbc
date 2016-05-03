CREATE DATABASE tripagency;

CREATE TABLE person(
name VARCHAR (100),
egn VARCHAR (10) PRIMARY KEY ,
age INT ,
email VARCHAR(50)
);

CREATE TABLE trip(
id SERIAL PRIMARY KEY,
egn VARCHAR (10), FOREIGN KEY(egn) REFERENCES person(egn) ON UPDATE CASCADE ON DELETE CASCADE,
dateArrived bigint,
dateDeparture bigint,
city VARCHAR (50)
);