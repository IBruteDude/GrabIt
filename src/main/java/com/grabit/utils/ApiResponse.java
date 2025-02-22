package com.grabit.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

	private boolean success;
	private int status;
	private String message;
	private T data;
	private List<String> errors;
	private String path;
	private ZonedDateTime timestamp;


	public static <T> ApiResponse<T> success(T data, String message) {
		return success(data, message, 0);
	}

	public static <T> ApiResponse<T> success(T data, String message, int status) {
		ApiResponse<T> response = new ApiResponse<>();
		response.success = true;
		response.status = status;
		response.message = message;
		response.data = data;
		response.errors = null;
		response.path = getRequestPath();
		response.timestamp = ZonedDateTime.now(ZoneOffset.UTC);
		return response;
	}

	public static <T> ApiResponse<T> error(List<String> errors, String message, int status) {
		ApiResponse<T> response = new ApiResponse<>();
		response.success = false;
		response.status = status;
		response.message = message;
		response.data = null;
		response.errors = errors;
		response.path = getRequestPath();
		response.timestamp = ZonedDateTime.now(ZoneOffset.UTC);
		return response;
	}

	public static <T> ApiResponse<T> error(String error, String message, int status) {
		return error(Arrays.asList(error), message, status);
	}

	public static <T> ApiResponse<T> error(String error, int status) {
		return error(error, HttpStatus.valueOf(status).getReasonPhrase(), status);
	}

	private static String getRequestPath() {
		ServletRequestAttributes attrs =
			(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	
		if (attrs == null) {
			throw new IllegalStateException("No request attributes found. This method must be called in the context of an HTTP request.");
		}
	
		HttpServletRequest request = attrs.getRequest();
		return request.getRequestURI();
	}
	
}
