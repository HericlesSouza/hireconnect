CREATE TABLE contracts (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "is_active" BOOLEAN NOT NULL DEFAULT TRUE,
    "department_id" UUID NOT NULL,
    "job_vacancy_id" UUID NOT NULL,
    "freelancer_id" UUID NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
    "updated_at" TIMESTAMP,
    CONSTRAINT fk_department_id FOREIGN KEY ("department_id") REFERENCES departments ("id"),
    CONSTRAINT fk_job_vacancy_id FOREIGN KEY ("job_vacancy_id") REFERENCES job_vacancies ("id"),
    CONSTRAINT fk_freelancer_id FOREIGN KEY ("freelancer_id") REFERENCES freelancers ("id")
)