package com.grabit.error;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error: ", ex);
        Locale locale = request.getLocale();
        String localizedMessage = messageSource.getMessage("error.unexpected", null, locale);

        return ApiResponse.error(localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
