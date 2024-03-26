CREATE TABLE IF NOT EXISTS team_members (
    team_id BIGINT(20),
    user_id BIGINT(20),
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES teams(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);