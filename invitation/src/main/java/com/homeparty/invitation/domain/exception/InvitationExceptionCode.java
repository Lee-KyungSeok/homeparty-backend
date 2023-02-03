package com.homeparty.invitation.domain.exception;

import abstraction.exeption.HomePartyExceptionCode;
import lombok.Getter;

@Getter
public enum InvitationExceptionCode implements HomePartyExceptionCode {

    INVITATION_SERVER_ERROR(500, "INVITATION_001", "Internal Server Error"),
    NOT_FOUND_INVITATION(404, "INVITATION_002", "not found invitation"),
    INVALID_INVITATION_CARD_CONTENT_TYPE(400, "INVITATION_003", "invalid content type of invitation card"),
    ;

    private final int status;
    private final String code;
    private final String message;

    InvitationExceptionCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
