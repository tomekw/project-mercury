CREATE TABLE messages (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  message_type_id uuid NOT NULL REFERENCES message_types (id) ON DELETE RESTRICT,
  message_status_id uuid NOT NULL REFERENCES message_statuses (id) ON DELETE RESTRICT,
  sender_id uuid NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  recipient_id uuid NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  body text NOT NULL,
  created_at timestamp without time zone NOT NULL,
  updated_at timestamp without time zone NOT NULL
);

CREATE INDEX index_messages_on_message_type_id ON messages (message_type_id);
CREATE INDEX index_messages_on_message_status_id ON messages (message_status_id);
CREATE INDEX index_messages_on_sender_id ON messages (sender_id);
CREATE INDEX index_messages_on_recipient_id ON messages (recipient_id);
CREATE INDEX index_messages_on_created_at ON messages (created_at);
