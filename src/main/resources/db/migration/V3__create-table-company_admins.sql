CREATE TABLE company_admins (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "user_id" UUID NOT NULL,
    "company_id" UUID NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
    "updated_at" TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY ("user_id") REFERENCES users ("id"),
    CONSTRAINT fk_company FOREIGN KEY ("company_id") REFERENCES companies ("id")
);
