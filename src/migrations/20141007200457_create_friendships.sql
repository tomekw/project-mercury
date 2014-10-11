CREATE TABLE friendships (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  friendship_status_id uuid NOT NULL REFERENCES friendship_statuses (id) ON DELETE RESTRICT,
  first_user_id uuid NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  second_user_id uuid NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  created_at timestamp without time zone NOT NULL,
  updated_at timestamp without time zone NOT NULL,
  CONSTRAINT not_friends_with_the_same_user_twice_check CHECK (first_user_id < second_user_id)
);

CREATE INDEX index_friendships_on_friendship_status_id ON friendships (friendship_status_id);
CREATE INDEX index_friendships_on_first_user_id ON friendships (first_user_id);
CREATE INDEX index_friendships_on_second_user_id ON friendships (second_user_id);
CREATE INDEX index_friendships_on_created_at ON friendships (created_at);

CREATE UNIQUE INDEX index_friendships_on_first_user_id_and_second_user_id
ON friendships (first_user_id, second_user_id);
