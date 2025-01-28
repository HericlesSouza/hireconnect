CREATE TABLE freelancers(
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "specialization" VARCHAR(200) NOT NULL,
    "bio" VARCHAR(2000) NOT NULL,
    "user_id" UUID NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
    "updated_at" TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY ("user_id") REFERENCES users ("id")
)