CREATE TABLE IF NOT EXISTS users(
            `id` bigint(20) NOT NULL AUTO_INCREMENT,
            `user_name` varchar(255) DEFAULT NULL,
            `full_name` varchar(255) NOT NULL,
            `email` varchar(255) unique NOT NULL,
            `password` varchar(255) DEFAULT NULL,
            `gender` varchar(255) NOT NULL,
            `nationality` varchar(255),
            `account_non_expired` bit(1) DEFAULT NULL,
            `account_non_locked` bit(1) DEFAULT NULL,
            `credentials_non_expired` bit(1) DEFAULT NULL,
            `enabled` bit(1) DEFAULT NULL,
            PRIMARY KEY (`id`),
            UNIQUE KEY `uk_user_name` (`user_name`)
) ENGINE=InnoDB;