ALTER TABLE mq135_readings ADD COLUMN team_handle VARCHAR(255) AFTER id;
ALTER TABLE mq135_readings ADD CONSTRAINT fk_mq135_team FOREIGN KEY (team_handle) REFERENCES teams(handle) ON DELETE CASCADE;

ALTER TABLE mq7_readings ADD COLUMN team_handle VARCHAR(255) AFTER id;
ALTER TABLE mq7_readings ADD CONSTRAINT fk_mq7_team FOREIGN KEY (team_handle) REFERENCES teams(handle) ON DELETE CASCADE;

ALTER TABLE fire_readings ADD COLUMN team_handle VARCHAR(255) AFTER id;
ALTER TABLE fire_readings ADD CONSTRAINT fk_fire_team FOREIGN KEY (team_handle) REFERENCES teams(handle) ON DELETE CASCADE;