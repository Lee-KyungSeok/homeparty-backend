CREATE TABLE `t_identity`
(
    `sequence`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `id`                   VARCHAR(64)         NOT NULL COMMENT '유저 id',
    `nickname`             varchar(50)         NULL     DEFAULT NULL COMMENT '닉네임',
    `email`                VARCHAR(128)        NULL     DEFAULT NULL COMMENT '이메일',

    `social_provider_type` VARCHAR(50)         NOT NULL DEFAULT 'NONE' COMMENT '소셜 회원가입 종류 (KAKAO, APPLE, EMAIL ...)',
    `social_id`            VARCHAR(255)        NULL     DEFAULT NULL COMMENT '소셜 로그인의 유니크한 id',
    `social_nickname`      VARCHAR(50)         NULL     DEFAULT NULL COMMENT '소셜 로그인시 받아온 닉네임',
    `social_email`         VARCHAR(128)        NULL     DEFAULT NULL COMMENT '소셜 로그인시 받아온 이메일',
    `created_at`           TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`           TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `left_at`              TIMESTAMP           NULL     DEFAULT NULL COMMENT '유저가 탈퇴한 시각, 탈퇴하지 않았다면 null',
    PRIMARY KEY (`sequence`),
    UNIQUE KEY `uix-identity-id` (`id`),
    UNIQUE KEY `uix-identity-social_info` (`social_id`, `social_provider_type`),
    KEY `idx-user_identity-email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '유저 인증 정보';
