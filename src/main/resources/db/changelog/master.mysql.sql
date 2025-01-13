-- liquibase formatted sql

-- changeset kamar:1733327638259-1
CREATE TABLE users (id INT NOT NULL, email VARCHAR(256) NULL, CONSTRAINT PK_USERS PRIMARY KEY (id));

