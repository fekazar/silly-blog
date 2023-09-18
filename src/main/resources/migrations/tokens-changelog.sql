--liquibase formatted sql

--changeset fyodor:create-tokens
CREATE TABLE IF NOT EXISTS token(
    id VARCHAR(255) PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    role VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
)
