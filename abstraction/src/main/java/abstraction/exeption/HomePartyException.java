package abstraction.exeption;

public abstract class HomePartyException extends RuntimeException {

    public HomePartyException(HomePartyExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
    }

    public abstract HomePartyExceptionCode getExceptionCode();
    public abstract String getErrorCode();
}