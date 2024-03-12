CREATE TABLE team_members (
                                            team_id BIGINT,
                                            user_id BIGINT,
                                            PRIMARY KEY (team_id, user_id),
                                            FOREIGN KEY (team_id) REFERENCES teams(id),
                                            FOREIGN KEY (user_id) REFERENCES users(id)
);