-- name: all-users
-- Returns all users
SELECT * FROM users;

-- name: user-by-id
-- Find user by ID
SELECT * FROM users WHERE id = :id::uuid;
