ALTER TABLE mq135_readings
    DROP FOREIGN KEY fk_mq135_team;

ALTER TABLE mq135_readings
    ADD CONSTRAINT fk_mq135_team
        FOREIGN KEY (team_handle)
            REFERENCES teams(handle)
            ON DELETE CASCADE
            ON UPDATE CASCADE;