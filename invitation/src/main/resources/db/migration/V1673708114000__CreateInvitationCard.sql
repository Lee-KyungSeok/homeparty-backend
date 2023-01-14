CREATE TABLE `t_invitation_card`
(
    `sequence`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `id`            VARCHAR(64)         NOT NULL COMMENT '초대장 댓글 id',
    `invitation_id` VARCHAR(64)         NULL     DEFAULT NULL COMMENT '초대장 id (초대장 없이 카드만 생성했다면 null)',
    `uploader_id`   VARCHAR(64)         NULL     DEFAULT NULL COMMENT '유저 id',
    `state`         VARCHAR(50)         NOT NULL COMMENT '카드 상태(생성됨, 사용됨, 삭제됨 등)',
    `file_path`     VARCHAR(255)        NOT NULL COMMENT '카드 이미지 url path(baseUrl 제외)',
    `used_at`       TIMESTAMP           NULL     DEFAULT NULL COMMENT '카드가 사용된 시각',
    `deleted_at`    TIMESTAMP           NULL     DEFAULT NULL COMMENT '카드가 삭제된 시각',
    `created_at`    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`sequence`),
    UNIQUE KEY `uix-invitation_card-id` (`id`),
    KEY `idx-invitation_card-invitation_id` (`invitation_id`),
    KEY `idx-invitation_card-uploader_id` (`uploader_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '초대장 카드 정보';

