package com.homeparty.api.exception;

import abstraction.exeption.HomePartyException;
import abstraction.exeption.HomePartyExceptionCode;

public class ApiException extends HomePartyException {

    private final ApiExceptionCode exceptionCode;

    public ApiException(ApiExceptionCode exceptionCode) {
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
        if (!(obj instanceof ApiException)) {
            return false;
        }
        return this.exceptionCode.getCode().equals(((ApiException) obj).exceptionCode.getCode());
    }
}
