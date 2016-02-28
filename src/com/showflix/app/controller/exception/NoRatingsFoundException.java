package com.showflix.app.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No Ratings are available")
public class NoRatingsFoundException extends Exception {
	private static final long serialVersionUID = 41334137507787975L;

}
