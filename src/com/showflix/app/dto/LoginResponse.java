package com.showflix.app.dto;

public class LoginResponse {

	private String authorizationToken;
	private String message;
	
	public LoginResponse(String authorizationToken,String message){
		this.setAuthorizationToken(authorizationToken);
		this.setMessage(message);
	}

	public String getAuthorizationToken() {
		return authorizationToken;
	}

	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
