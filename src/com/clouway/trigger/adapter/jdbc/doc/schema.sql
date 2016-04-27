CREATE DATABASE store;

CREATE TABLE vendor(
id SERIAL PRIMARY KEY,
firstName VARCHAR(50),
lastName VARCHAR (50),
age int
);

CREATE TABLE backupVendor(
id SERIAL,
firstName VARCHAR(50),
lastName VARCHAR(50),
age int
);

create function doBackup() returns trigger as 'BEGIN
insert into backupVendor(id, firstname, lastname, age) values(old.id,old.firstName,old.lastname,old.age);
return new;
end;'
language plpgsql;

create trigger backup after UPDATE on vendor for each row execute procedure doBackup();
