ALTER TABLE notification_preferences
    ADD COLUMN fire_notification_interval_minutes INT DEFAULT 10 NOT NULL AFTER fire_detection;