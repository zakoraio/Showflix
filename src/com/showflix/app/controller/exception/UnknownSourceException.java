package com.showflix.app.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Request Parameters are not valid")
public class UnknownSourceException extends Exception {
	private static final long serialVersionUID = 1088225159565552389L;
	
}
