package com.showflix.app.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="User already Exists")
public class UserAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 5968000547444142953L;
}
