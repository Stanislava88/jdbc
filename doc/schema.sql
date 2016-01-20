create database test;

use test;

create table users(id int primary key auto_increment, email varchar(50), name varchar(50));

create database task2;

use task2;

create table people( EGN int not null, name VARCHAR(20) not null, age int(3) not null, email VARCHAR(40) not null, primary key(EGN));

create table trip( EGN int not null, date_arrive DATE not null, departure_date DATE not null, city varchar(20) not null,
 trip_id int not null auto_increment, primary key (trip_id), foreign key(EGN) references people(EGN));