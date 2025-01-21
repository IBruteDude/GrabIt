-- Create the 'users' table with case-insensitive collation
CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL,
    CONSTRAINT unique_username UNIQUE (username)
) ENGINE=InnoDB;

-- Create the 'authorities' table with case-insensitive collation
CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username),
    CONSTRAINT unique_authority UNIQUE (username, authority)
) ENGINE=InnoDB;

-- Create index for faster lookups on authorities (username, authority)
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);
