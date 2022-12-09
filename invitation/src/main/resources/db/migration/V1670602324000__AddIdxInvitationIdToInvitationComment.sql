CREATE INDEX `idx-invitation_comment-invitation_deleted`
    ON `t_invitation_comment` (`invitation_id`, `is_deleted`);