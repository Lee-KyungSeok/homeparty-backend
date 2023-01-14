CREATE TABLE `t_invitation_comment`
(
    `sequence`     BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `id`           VARCHAR(64)         NOT NULL COMMENT '초대장 댓글 id',
    `user_id`      VARCHAR(64)         NULL     DEFAULT NULL COMMENT '댓글 작성자 id(로그인 안할시 null)',
    `name`         VARCHAR(128)        NOT NULL COMMENT '댓글 작성자 이름',
    `content`      VARCHAR(255)        NOT NULL COMMENT '댓글 내용',
    `is_secret`    BOOLEAN             NOT NULL COMMENT '비밀댓글 여부',
    `commented_at` TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '댓글 작성 시각',
    `is_deleted`   BOOLEAN             NOT NULL DEFAULT false COMMENT '댓글 삭제 여부',
    `deleted_at`   TIMESTAMP           NULL     DEFAULT NULL COMMENT '댓글 삭제된 시각',
    `created_at`   TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`sequence`),
    UNIQUE KEY `uix-invitation_comment-id` (`id`),
    KEY `idx-invitation_comment-user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '초대장 댓글 정보';
