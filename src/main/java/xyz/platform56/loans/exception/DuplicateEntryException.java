package xyz.platform56.loans.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DuplicateEntryException extends RuntimeException {


    private String errorCode;

    public DuplicateEntryException() {
    }

    public DuplicateEntryException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
