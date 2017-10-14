package xyz.platform56.loans.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class ApiException extends RuntimeException {


    private String errorCode;

    public ApiException() {
    }

    public ApiException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
