
 create table customer(
 id serial,
 firstName varchar(50),
 lastName varchar(50),
 egn varchar(50),
 phoneNumber varchar(50)
 );

 create index index on customer(id,egn);

