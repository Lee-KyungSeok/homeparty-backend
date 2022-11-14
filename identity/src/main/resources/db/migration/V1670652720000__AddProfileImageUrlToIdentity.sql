ALTER TABLE `t_identity`
    ADD COLUMN `social_image_url` VARCHAR(510) NULL COMMENT '소셜 이미지 url' AFTER `social_email`;

ALTER TABLE `t_identity`
    ADD COLUMN `profile_image_url` VARCHAR(255) NULL COMMENT '프로필 이미지 url' AFTER `email`;