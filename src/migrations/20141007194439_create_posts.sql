CREATE TABLE posts (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  sender_id uuid NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  recipient_id uuid NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  body text NOT NULL,
  created_at timestamp without time zone NOT NULL,
  updated_at timestamp without time zone NOT NULL
);

CREATE INDEX index_posts_on_sender_id ON posts (sender_id);
CREATE INDEX index_posts_on_recipient_id ON posts (recipient_id);
CREATE INDEX index_posts_on_created_at ON posts (created_at);
