-- liquibase formatted sql

--changeset fyodor:1
CREATE TABLE IF NOT EXISTS article(
    id SERIAL PRIMARY KEY,
    header TEXT NOT NULL,
    body TEXT NOT NULL,
    creation_date TIMESTAMP
);

--rollback DROP TABLE IF EXISTS article;
