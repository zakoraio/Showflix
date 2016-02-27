package com.showflix.app.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Show already Exists")
public class ShowDetailsAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 453170038154305050L;

}
