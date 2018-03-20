CREATE TABLE `email_audit_trail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reference` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `template_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `incoming_request` TEXT COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_content` MEDIUMTEXT COLLATE utf8mb4_unicode_ci NOT NULL,
  `recipient_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sent_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;