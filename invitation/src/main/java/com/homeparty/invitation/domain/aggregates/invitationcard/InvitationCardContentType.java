package com.homeparty.invitation.domain.aggregates.invitationcard;

import com.homeparty.invitation.domain.exception.InvitationException;
import com.homeparty.invitation.domain.exception.InvitationExceptionCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum InvitationCardContentType {
    JPEG("jpeg", "image/jpeg"),
    JPG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    BMP("bmp", "image/bmp")
    ;

    private final String extension;
    private final String contentType;

    InvitationCardContentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }


    public static InvitationCardContentType getInvitationCardContentType(String contentType) {
        return Arrays.stream(InvitationCardContentType.values())
                .filter(it -> it.contentType.equals(contentType.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new InvitationException(InvitationExceptionCode.INVALID_INVITATION_CARD_CONTENT_TYPE));
    }
}
