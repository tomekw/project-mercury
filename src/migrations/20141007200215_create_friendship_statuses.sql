CREATE TABLE friendship_statuses (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name text NOT NULL
);

CREATE UNIQUE INDEX index_friendship_statuses_on_name ON friendship_statuses (name);
