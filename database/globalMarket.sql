-- globalMarket.api_secret definition

CREATE TABLE `api_secret` (
                              `secret_id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `secret` varchar(50) DEFAULT NULL,
                              PRIMARY KEY (`secret_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- globalMarket.authority definition

CREATE TABLE `authority` (
                             `authority_name` varchar(50) NOT NULL,
                             PRIMARY KEY (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- globalMarket.category_naver definition

CREATE TABLE `category_naver` (
                                  `last` bit(1) NOT NULL,
                                  `category_naver_id` bigint(20) NOT NULL,
                                  `name` varchar(255) NOT NULL,
                                  `whole_category_name` varchar(255) NOT NULL,
                                  PRIMARY KEY (`category_naver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- globalMarket.market definition

CREATE TABLE `market` (
                          `activated` bit(1) DEFAULT NULL,
                          `market_id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `username` varchar(20) DEFAULT NULL,
                          `password` varchar(100) DEFAULT NULL,
                          `api_secret` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`market_id`),
                          UNIQUE KEY `UK_9ofdb81ao52tnefnqeuqh9a2l` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- globalMarket.product definition

CREATE TABLE `product` (
                           `sale_price` int(11) NOT NULL,
                           `sell_count` int(11) NOT NULL,
                           `market_id` bigint(20) NOT NULL,
                           `product_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `product_number` bigint(20) DEFAULT NULL,
                           `outsourced_url` varchar(255) DEFAULT NULL,
                           `platform_type` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`product_id`),
                           KEY `FKnd0xf8hu7ixgw6u0do43xp2fb` (`market_id`),
                           CONSTRAINT `FKnd0xf8hu7ixgw6u0do43xp2fb` FOREIGN KEY (`market_id`) REFERENCES `market` (`market_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- globalMarket.user_authority definition

CREATE TABLE `user_authority` (
                                  `market_id` bigint(20) NOT NULL,
                                  `authority_name` varchar(50) NOT NULL,
                                  PRIMARY KEY (`market_id`,`authority_name`),
                                  KEY `FK6ktglpl5mjosa283rvken2py5` (`authority_name`),
                                  CONSTRAINT `FK1w5q3stlj44nmw5hlwbyaeqjw` FOREIGN KEY (`market_id`) REFERENCES `market` (`market_id`),
                                  CONSTRAINT `FK6ktglpl5mjosa283rvken2py5` FOREIGN KEY (`authority_name`) REFERENCES `authority` (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;