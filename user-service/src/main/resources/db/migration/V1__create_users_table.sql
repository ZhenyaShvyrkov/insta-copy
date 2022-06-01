CREATE SEQUENCE user_id_generator START 1 INCREMENT 1;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    external_id UUID NOT NULL UNIQUE,
    name VARCHAR(64) NOT NULL,
    email VARCHAR(128) NOT NULL,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    phone_number VARCHAR(20),
    bio VARCHAR(512),
    gender VARCHAR(32) NOT NULL,
    role VARCHAR(16) NOT NULL,
    website VARCHAR(64),
    photo_url VARCHAR(256),
    is_deleted BOOLEAN NOT NULL,
    created_at TIMESTAMP,
    modified_at TIMESTAMP
);

CREATE TABLE users_followers (
    target_id BIGINT NOT NULL,
    follower_id BIGINT NOT NULL,
    PRIMARY KEY (target_id, follower_id)
);