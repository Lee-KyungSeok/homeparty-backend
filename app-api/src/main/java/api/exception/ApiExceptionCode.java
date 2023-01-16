package api.exception;

import abstraction.exeption.HomePartyExceptionCode;
import lombok.Getter;

@Getter
public enum ApiExceptionCode implements HomePartyExceptionCode {

    INTERNAL_SERVER_ERROR(500, "APP_001", "Internal Server Error"),
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
