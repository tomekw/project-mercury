CREATE TABLE message_statuses (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name text NOT NULL
);

CREATE UNIQUE INDEX index_message_statuses_on_name ON message_statuses (name);
