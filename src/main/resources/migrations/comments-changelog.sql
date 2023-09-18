--liquibase formatted sql

--changeset fyodor:create-comments
CREATE TABLE IF NOT EXISTS comment(
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    author VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    article_id INTEGER REFERENCES article(id) NOT NULL,
    response_to INTEGER REFERENCES comment(id)
)

--rollback DROP TABLE IF EXISTS comment;
