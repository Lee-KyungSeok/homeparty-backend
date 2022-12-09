ALTER TABLE t_invitation_comment
    ADD COLUMN `invitation_id` VARCHAR(64) NOT NULL COMMENT '초대장 id' AFTER id;