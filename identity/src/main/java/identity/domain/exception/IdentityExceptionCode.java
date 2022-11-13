package identity.domain.exception;

import lombok.Getter;

@Getter
public enum IdentityExceptionCode {

    IDENTITY_SERVER_ERROR(500, "IDENTITY_001", "Internal Server Error"),
    ALREADY_SIGN_UP(400, "IDENTITY_002", "already sign up user"),
    ;

    private final int status;
    private final String code;
    private final String message;

    IdentityExceptionCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
