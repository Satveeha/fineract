package org.apache.fineract.portfolio.naaccount.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ApiException.class })
    public ResponseEntity<Object> handleApiException(final ApiException apiException, final WebRequest webRequest) {
        logger.error("API Exception: {}", apiException);
        final ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(apiException.getErrorMessage());
        return handleExceptionInternal(apiException, apiExceptionResponse, new HttpHeaders(), apiException.getHttpStatus(), webRequest);
    }
}
