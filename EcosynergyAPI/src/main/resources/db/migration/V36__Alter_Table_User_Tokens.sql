ALTER TABLE user_tokens RENAME COLUMN device_type TO platform;

ALTER TABLE user_tokens MODIFY COLUMN platform VARCHAR(10) NOT NULL;