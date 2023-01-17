package com.homeparty.identity.domain.exception;

import abstraction.exeption.HomePartyException;
import abstraction.exeption.HomePartyExceptionCode;

public class IdentityException extends HomePartyException {

    private final IdentityExceptionCode exceptionCode;

    public IdentityException(IdentityExceptionCode exceptionCode) {
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
        if (!(obj instanceof IdentityException)) {
            return false;
        }
        return this.exceptionCode.getCode().equals(((IdentityException) obj).exceptionCode.getCode());
    }
}
