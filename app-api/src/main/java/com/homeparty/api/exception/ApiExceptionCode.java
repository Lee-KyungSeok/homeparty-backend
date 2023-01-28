package com.homeparty.api.exception;

import abstraction.exeption.HomePartyExceptionCode;
import lombok.Getter;

@Getter
public enum ApiExceptionCode implements HomePartyExceptionCode {

    INTERNAL_SERVER_ERROR(500, "APP_001", "internal server error"),
    BAD_REQUEST(400, "APP_002", "bad request error"),
    FAIL_TO_LOGIN(400, "APP_003", "failed to login"),
    UNAUTHORIZED_ERROR(401, "APP_004", "Unauthorized error"),
    FORBIDDEN_ERROR(403, "APP_005", "Forbidden error"),
    INVALID_ACCESS_TOKEN(401, "APP_006", "Invalid access token"),
    NOT_FOUND_ERROR(404, "APP_007", "Not found error"),
    ;

    private final int status;
    private final String code;
    private final String message;

    ApiExceptionCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
