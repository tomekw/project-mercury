CREATE TABLE users (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name text NOT NULL DEFAULT '',
  email text NOT NULL,
  encryption_key text,
  challenge_text text,
  created_at timestamp without time zone NOT NULL,
  updated_at timestamp without time zone NOT NULL
);

CREATE UNIQUE INDEX index_users_on_lower_email ON users (LOWER(email));
CREATE UNIQUE INDEX index_users_on_encryption_key ON users (encryption_key);
CREATE UNIQUE INDEX index_users_on_challenge_text ON users (challenge_text);
CREATE INDEX index_users_on_created_at ON users (created_at);
