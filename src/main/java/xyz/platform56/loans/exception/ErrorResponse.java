package xyz.platform56.loans.exception;

import java.util.Map;

/**
 * Created by afernandez on 28/06/2017.
 */
public class ErrorResponse {

    public Integer status;
    public String message;
    public String timeStamp;

    public ErrorResponse(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.message = (String) errorAttributes.get("message");
        this.timeStamp = errorAttributes.get("timestamp").toString();
    }

    public ErrorResponse(int status, Map<String, Object> errorAttributes, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = errorAttributes.get("timestamp").toString();
    }

}