package com.showflix.app.service.exceptions;

public class ServiceException extends Exception {


	private static final long serialVersionUID = -5488446704956650423L;
	
	public ServiceException(){
		super();
	}
	
	public ServiceException(String message){
		super(message);
	}
	
	public ServiceException(String message , Throwable cause){
		super(message,cause);
	}
	
	public ServiceException(Throwable cause){
		super(cause);
	}

}
