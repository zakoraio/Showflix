package com.showflix.app.dto;

public class LoginResponse {

	private String authorizationToken;
	
	public LoginResponse(String authorizationToken){
		this.setAuthorizationToken(authorizationToken);
	}

	public String getAuthorizationToken() {
		return authorizationToken;
	}

	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}
	
}
