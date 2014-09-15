-- name: all-users
-- Returns all users
SELECT * FROM users;

-- name: find-user
-- Returns user by ID
SELECT * FROM users WHERE id = :id;
