package com.homeparty.invitation.domain.models;

import com.homeparty.invitation.domain.aggregates.invitationcard.InvitationCardContentType;

import java.net.URL;

public interface InvitationCardUploadUrlFetcher {
    InvitationCardUploadUrlResult fetch(InvitationCardUploadUrlParams params);

    record InvitationCardUploadUrlParams(
            String filePath,
            InvitationCardContentType contentType
    ) {}

    record InvitationCardUploadUrlResult(
            URL uploadUrl,
            String httpMethod,
            Object uploadData
    ) {}
}
