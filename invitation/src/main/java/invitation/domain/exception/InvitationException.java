package invitation.domain.exception;

public class InvitationException extends RuntimeException {

    private final InvitationExceptionCode exceptionCode;

    public InvitationException(InvitationExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public InvitationExceptionCode getExceptionCode() {
        return exceptionCode;
    }

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
