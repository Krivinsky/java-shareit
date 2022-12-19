CREATE TABLE IF NOT EXISTS users
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(100),
    email varchar(320)
);

CREATE TABLE IF NOT EXISTS itemRequests
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    description VARCHAR(1000),
    user_id integer references users (id),
    created datetime
);

CREATE TABLE IF NOT EXISTS items
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(100),
    description VARCHAR(1000),
    available boolean,
    user_id integer references users (id),
    request_id integer references itemRequests(id)
);

