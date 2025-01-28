CREATE TYPE job_applications_status AS ENUM ('REJECTED', 'PENDING', 'ACCEPTED');

CREATE TABLE job_vacancies_applications(
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "status" job_applications_status NOT NULL,
    "job_vacancy_id" UUID NOT NULL,
    "freelancer_id" UUID NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
    "updated_at" TIMESTAMP,
    CONSTRAINT fk_job_vacancy FOREIGN KEY ("job_vacancy_id") REFERENCES job_vacancies ("id")
    CONSTRAINT fk_freelancer FOREIGN KEY ("freelancer_id") REFERENCES freelancers ("id")
)