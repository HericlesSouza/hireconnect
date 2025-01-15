CREATE TABLE job_vacancies (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "title" VARCHAR(50) NOT NULL,
    "description" TEXT NOT NULL,
    "is_active" BOOLEAN NOT NULL DEFAULT TRUE,
    "department_id" UUID NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
    "updated_at" TIMESTAMP,
    CONSTRAINT fk_department FOREIGN KEY ("department_id") REFERENCES departments ("id")
);

CREATE INDEX idx_job_vacancies_department_id ON job_vacancies ("department_id");