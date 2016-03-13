create TABLE address (
    id integer not null,
    user_id integer not null,
    address text not null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
create TABLE contact (
    id integer not null,
    user_id integer not null,
    phone_number text not null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
create TABLE users (
    id integer PRIMARY KEY not null,
    name text not null
);