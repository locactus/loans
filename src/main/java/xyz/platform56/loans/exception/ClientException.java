package xyz.platform56.loans.exception;

/**
 * All 4xx exception like validation, not found and etc.
 */
public class ClientException extends ApiException {

    public ClientException() {
    }

    public ClientException(String code, String msg) {
        super(code, msg);
    }
}
