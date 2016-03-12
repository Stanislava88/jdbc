create TABLE people (
    name text not null,
    egn text not null,
    age integer,
    email text,
    PRIMARY KEY (egn)
);
create TABLE trip (
    id integer not null default nextval('trip_id_seq'::regclass),
    egn text References people(egn),
    arrival date not null,
    departure date not null,
    city text,
PRIMARY KEY (id)
);
CREATE UNIQUE INDEX trip_pkey ON trip (id);