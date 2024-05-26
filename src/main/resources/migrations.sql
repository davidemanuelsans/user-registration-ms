CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    created date,
    modified date,
    last_login date,
    last_token VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE phone_numbers (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    number VARCHAR(50) NOT NULL,
    type VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_users_email ON users(email);

CREATE INDEX idx_users_role ON users(role);