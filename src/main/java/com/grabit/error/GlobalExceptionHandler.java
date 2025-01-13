package com.grabit.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	ResponseEntity<?> handle(Exception e) {
		System.out.printf("Handled exception %s", e.toString());
		return ResponseEntity.badRequest().body(e);
	}

}
