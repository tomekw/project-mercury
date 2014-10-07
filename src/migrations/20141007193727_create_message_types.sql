CREATE TABLE message_types (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name text NOT NULL
);

CREATE UNIQUE INDEX index_message_types_on_name ON message_types (name);
