CREATE TABLE IF NOT EXISTS users
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(100),
    email varchar(320)
);

CREATE TABLE IF NOT EXISTS requests
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    description  VARCHAR(1000),
    requestor_id integer references users (id)
);

CREATE TABLE IF NOT EXISTS items
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(100),
    description VARCHAR(1000),
    available boolean,
    owner_id integer references users (id),
    request_id integer references requests(id)
);

CREATE TABLE IF NOT EXISTS items
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255),
    description varchar(255),
    is_available boolean default false,
    owner_id integer references users (id),
    request_id integer references requests (id)
);

CREATE TABLE IF NOT EXISTS bookings
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    start_date timestamp without time zone,
    end_date timestamp without time zone,
    item_id integer references items (id),
    booker_id integer references users (id),
    status varchar (50)
);

CREATE TABLE IF NOT EXISTS comments
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    text VARCHAR(100),
    item_id integer references items (id),
    author_id integer references users (id),
    preated timestamp without time zone
);