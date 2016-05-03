CREATE DATABASE userinformation;

CREATE TABLE users(
id int PRIMARY KEY,
firstName VARCHAR(50),
lastName VARCHAR (50),
age int
);

CREATE TABLE address(
id int PRIMARY KEY,
city VARCHAR(50),
street VARCHAR(100),
number int
);

CREATE TABLE newContact(
id int PRIMARY KEY,
idUser int, FOREIGN KEY(id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
idAddress int,FOREIGN KEY(id) REFERENCES address(id) ON UPDATE CASCADE ON DELETE CASCADE
);