-- Remove existing foreign keys
ALTER TABLE team_members
DROP FOREIGN KEY team_members_ibfk_1;

ALTER TABLE team_members
DROP FOREIGN KEY team_members_ibfk_2;

-- Re-add existing foreign keys with ON DELETE CASCADE
ALTER TABLE team_members
ADD CONSTRAINT team_members_ibfk_1
FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE;

ALTER TABLE team_members
ADD CONSTRAINT team_members_ibfk_2
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;