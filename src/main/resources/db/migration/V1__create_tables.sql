CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS department
(
    id uuid PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    name varchar(255)
    );