CREATE TABLE `t_invitation`
(
    `sequence`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `id`            VARCHAR(64)         NOT NULL COMMENT '초대장 id',
    `host_id`       VARCHAR(64)         NOT NULL COMMENT '파티 개최자 id',
    `title`         VARCHAR(128)        NOT NULL COMMENT '파티 제목',
    `description`   VARCHAR(255)        NOT NULL COMMENT '설명',
    `type`          VARCHAR(50)         NOT NULL COMMENT '파티 유형',
    `partied_at`    TIMESTAMP           NOT NULL COMMENT '파티 일시',
    `location_name` VARCHAR(100)        NOT NULL COMMENT '파티 장소 이름',
    `latitude`      DECIMAL(18, 10)     NULL     DEFAULT NULL COMMENT '파티 장소 위도',
    `longitude`     DECIMAL(18, 10)     NULL     DEFAULT NULL COMMENT '파티 장소 경도',
    `created_at`    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`sequence`),
    UNIQUE KEY `uix-invitation-id` (`id`),
    KEY `idx-invitation-host` (`host_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '초대장 정보';
