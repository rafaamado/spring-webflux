CREATE TABLE card (
  id BIGSERIAL PRIMARY KEY,
  deck_id INT NOT NULL,
  front VARCHAR(200) NOT NULL,
  back VARCHAR(200) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NULL,
  FOREIGN KEY (deck_id) REFERENCES deck (id) ON DELETE CASCADE
);