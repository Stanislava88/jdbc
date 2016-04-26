CREATE DATABASE store;

CREATE TABLE vendor(
idVendor SERIAL PRIMARY KEY,
firstName VARCHAR(50),
lastName VARCHAR (50),
age int
);

CREATE TABLE backupVendor(
idVendor SERIAL,
firstName VARCHAR(50),
lastName VARCHAR(50),
age int
);

create function updateFunction() returns trigger as 'BEGIN
insert into backupVendor(idvendor, firstname, lastname, age) values(old.idVendor,old.firstName,old.lastname,old.age);
return new;
end;'
language plpgsql;

create trigger backup after INSERT,UPDATE,DELETE on vendor for each row execute procedure updateFunction();
