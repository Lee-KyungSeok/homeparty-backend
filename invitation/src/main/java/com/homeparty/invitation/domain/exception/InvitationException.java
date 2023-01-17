package com.homeparty.invitation.domain.exception;

import abstraction.exeption.HomePartyException;
import abstraction.exeption.HomePartyExceptionCode;

public class InvitationException extends HomePartyException {

    private final InvitationExceptionCode exceptionCode;

    public InvitationException(InvitationExceptionCode exceptionCode) {
        super(exceptionCode);
        this.exceptionCode = exceptionCode;
    }

    @Override
    public HomePartyExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    @Override
    public String getErrorCode() {
        return exceptionCode.getCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InvitationException)) {
            return false;
        }
        return this.exceptionCode.getCode().equals(((InvitationException) obj).exceptionCode.getCode());
    }
}
