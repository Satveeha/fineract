package org.apache.fineract.portfolio.naaccount.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final String errorMessage;
    private final HttpStatus httpStatus;

    public ApiException(final String errorMessage, final HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    String getErrorMessage() {
        return errorMessage;
    }

    HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
