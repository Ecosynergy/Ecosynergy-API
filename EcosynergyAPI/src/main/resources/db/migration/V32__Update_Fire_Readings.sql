ALTER TABLE fire_readings
    DROP FOREIGN KEY fk_fire_team;

ALTER TABLE fire_readings
    ADD CONSTRAINT fk_fire_team
        FOREIGN KEY (team_handle)
            REFERENCES teams(handle)
            ON DELETE CASCADE
            ON UPDATE CASCADE;