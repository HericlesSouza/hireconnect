CREATE TYPE type_user AS ENUM ('FREELANCER', 'COMPANY', 'ADMIN');

CREATE TABLE users (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "name" VARCHAR(50) NOT NULL,
    "email" VARCHAR(50) NOT NULL UNIQUE,
    "password" VARCHAR(150) NOT NULL,
    "img_url" VARCHAR(2000),
    "type_user" type_user NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
    "updated_at" TIMESTAMP
);

CREATE INDEX idx_users_email ON users (email);