CREATE TABLE departments(
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "description" TEXT NOT NULL,
    "company_id" UUID NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
    "updated_at" TIMESTAMP,
    CONSTRAINT fk_company FOREIGN KEY ("company_id") REFERENCES companies ("id")
);

CREATE INDEX idx_departments_company_id ON departments ("company_id");