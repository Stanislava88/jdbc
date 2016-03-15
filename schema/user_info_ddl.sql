create TABLE address (
    id BIGINT not null,
    user_id BIGINT not null,
    address text not null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
create TABLE contact (
    id BIGINT not null,
    user_id BIGINT not null,
    phone_number text not null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
create TABLE users (
    id BIGINT PRIMARY KEY not null,
    name text not null
);