ALTER TABLE teams
ADD COLUMN activity_id BIGINT(20) NOT NULL DEFAULT 1 AFTER description;

ALTER TABLE teams
ADD CONSTRAINT fk_activity
FOREIGN KEY (activity_id)
REFERENCES Activities(id);