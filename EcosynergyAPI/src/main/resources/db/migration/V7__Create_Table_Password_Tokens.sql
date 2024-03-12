CREATE TABLE `passwordtokens` (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `token` varchar(255) NOT NULL,
                                  `user_id` BIGINT NOT NULL,
                                  `used` tinyint(1) NOT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `passwordTokens` (`user_id`),
                                  CONSTRAINT `passwordtokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);