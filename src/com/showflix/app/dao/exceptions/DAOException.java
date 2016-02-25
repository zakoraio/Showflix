package com.showflix.app.dao.exceptions;

public class DAOException extends Exception {

	private static final long serialVersionUID = 7398332219116499864L;
	
	public DAOException(){
		super();
	}
	
	public DAOException(String message){
		super(message);
	}
	
	public DAOException(String message , Throwable cause){
		super(message, cause);
	}

	public DAOException(Throwable cause){
		super(cause);
	}
}
