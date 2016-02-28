package com.showflix.app.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="User Not authorized")
public class UnauthorizedException extends Exception {
	private static final long serialVersionUID = 1088225159565552389L;
	
}
