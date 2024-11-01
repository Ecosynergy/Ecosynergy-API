ALTER TABLE mq7_readings
    DROP FOREIGN KEY fk_mq7_team;

ALTER TABLE mq7_readings
    ADD CONSTRAINT fk_mq7_team
        FOREIGN KEY (team_handle)
            REFERENCES teams(handle)
            ON DELETE CASCADE
            ON UPDATE CASCADE;