--liquibase formatted sql

--changeset fyodor:create-tokens
CREATE TABLE IF NOT EXISTS token(
    id VARCHAR(255) PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    role VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

INSERT INTO token (id, creation_date, role, description) VALUES (
    'admindefault', now(), 'ROLE_ADMIN', 'Default generated token, should be deleted.'
);

--rollback DROP TABLE IF EXISTS token;
