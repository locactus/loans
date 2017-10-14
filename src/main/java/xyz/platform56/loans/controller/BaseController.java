package xyz.platform56.loans.controller;

import xyz.platform56.loans.exception.ApiException;
import xyz.platform56.loans.exception.DuplicateEntryException;
import xyz.platform56.loans.exception.ErrorResponse;
import xyz.platform56.loans.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
public class BaseController {

    @Autowired
    private ErrorAttributes errorAttributes;


    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Exception> handleNoResultException(
            NoResultException nre) {

        return new ResponseEntity<Exception>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleException(Exception e) {
        return new ResponseEntity<Exception>(e,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundError(HttpServletRequest req) {
        return emitErrorJson(HttpStatus.NOT_FOUND, req, false);
    }


    @ExceptionHandler(DuplicateEntryException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleDuplicate(HttpServletRequest req) {
        return emitErrorJson(HttpStatus.CONFLICT, req, false);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationFromAnnotation(HttpServletRequest req, MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        FieldError error = result.getFieldError();
        return emitErrorJson(HttpStatus.BAD_REQUEST, req, false, error.getDefaultMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException exception) {
        return emitErrorJson(HttpStatus.BAD_REQUEST, req, false, exception.getLocalizedMessage());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentExceptionForNotReadable(HttpServletRequest req) {
        return emitErrorJson(HttpStatus.BAD_REQUEST, req, false);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentExceptionTypeMismatch(HttpServletRequest req) {
        return emitErrorJson(HttpStatus.BAD_REQUEST, req, false);
    }

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAPIException(HttpServletRequest req) {

        return emitErrorJson(HttpStatus.BAD_REQUEST, req, false);
    }


    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

    private ErrorResponse emitErrorJson(HttpStatus status, HttpServletRequest request, boolean includeStackTrace, String message) {
        return new ErrorResponse(status.value(), getErrorAttributes(request, includeStackTrace), message);
    }

    private ErrorResponse emitErrorJson(HttpStatus status, HttpServletRequest request, boolean includeStackTrace) {
        return new ErrorResponse(status.value(), getErrorAttributes(request, includeStackTrace));
    }
}
