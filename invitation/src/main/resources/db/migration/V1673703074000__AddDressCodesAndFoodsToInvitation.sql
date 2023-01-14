ALTER TABLE `t_invitation`
    ADD COLUMN `dress_codes` VARCHAR(255) NULL DEFAULT NULL COMMENT '드레스 코드' AFTER `type`;

ALTER TABLE `t_invitation`
    ADD COLUMN `foods` VARCHAR(255) NULL DEFAULT NULL COMMENT '드레스 코드' AFTER `dress_codes`;