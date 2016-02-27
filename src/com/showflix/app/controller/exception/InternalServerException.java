package com.showflix.app.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Coludnt retrive data")
public class InternalServerException extends Exception {
	private static final long serialVersionUID = 1088225159565552389L;
	
}
