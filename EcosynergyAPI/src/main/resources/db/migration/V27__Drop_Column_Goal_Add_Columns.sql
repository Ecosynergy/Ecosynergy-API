ALTER TABLE teams DROP COLUMN goal;

ALTER TABLE teams ADD COLUMN daily_goal DECIMAL(10, 2) AFTER activity_id;
ALTER TABLE teams ADD COLUMN weekly_goal DECIMAL(10, 2) AFTER daily_goal;
ALTER TABLE teams ADD COLUMN monthly_goal DECIMAL(10, 2) AFTER weekly_goal;
ALTER TABLE teams ADD COLUMN annual_goal DECIMAL(10, 2) AFTER monthly_goal;