CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS department
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
    );