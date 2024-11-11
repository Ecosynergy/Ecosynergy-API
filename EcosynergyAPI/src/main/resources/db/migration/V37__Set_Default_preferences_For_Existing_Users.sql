INSERT INTO notification_preferences (user_id, platform, fire_detection, invite_status, invite_received, team_goal_reached, created_at, updated_at)
SELECT
    id AS user_id,
    'WEB' AS platform,
    TRUE AS fire_detection,
    TRUE AS invite_status,
    TRUE AS invite_received,
    TRUE AS team_goal_reached,
    CURRENT_TIMESTAMP AS created_at,
    CURRENT_TIMESTAMP AS updated_at
FROM users;

INSERT INTO notification_preferences (user_id, platform, fire_detection, invite_status, invite_received, team_goal_reached, created_at, updated_at)
SELECT
    id AS user_id,
    'ANDROID' AS platform,
    TRUE AS fire_detection,
    TRUE AS invite_status,
    TRUE AS invite_received,
    TRUE AS team_goal_reached,
    CURRENT_TIMESTAMP AS created_at,
    CURRENT_TIMESTAMP AS updated_at
FROM users;