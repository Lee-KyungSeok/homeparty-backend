package com.homeparty.invitation.aws;

public record AwsConfig(
        String invitationCardBucket,
        int invitationCardUploadDuration
) {}
