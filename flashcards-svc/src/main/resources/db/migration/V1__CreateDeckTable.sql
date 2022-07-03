CREATE TABLE deck (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(50),
  user_id BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NULL
);