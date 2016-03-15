package com.showflix.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.showflix.app.constants.ApplicationConstants;
import com.showflix.app.controller.exception.InternalServerException;
import com.showflix.app.controller.exception.UserAlreadyExistsException;
import com.showflix.app.controller.exception.UserNotFoundException;
import com.showflix.app.dao.entity.Role;
import com.showflix.app.dao.entity.User;
import com.showflix.app.dto.LoginInfo;
import com.showflix.app.dto.LoginResponse;
import com.showflix.app.dto.Message;
import com.showflix.app.service.IUserService;
import com.showflix.app.service.exceptions.ServiceException;
import com.showflix.app.util.AuthenticationUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/login")
@Api(tags="login")
public class LoginController {
	
	
	@Autowired
	IUserService userService;

	@RequestMapping(method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Verify Login Info and Logs user in the system",
			notes="Return a success message on successful login")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=401, message="Unauthorized"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public LoginResponse login(@RequestBody(required=true) LoginInfo loginInfo) throws InternalServerException, UserNotFoundException {
		User user = null;
		List<Role> roles = null;
		LoginResponse response = new LoginResponse(null,"Invalid Username/Password");
		try {
			user = userService.getUserbyUserName(loginInfo.getUserName());
			if(user.getPassword().equals(loginInfo.getPassword())){
				roles = (List<Role>) user.getRoles();
				Role tmpRole = new Role();
				tmpRole.setName(loginInfo.getRole());
				if(roles.contains(tmpRole)){
				String token = AuthenticationUtil.generateToken(loginInfo.getUserName(), roles.get(0).getName(), ApplicationConstants.secretKey);
				response = new LoginResponse(token,"Login Successful");
				}
			}
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		
		return response;
	}
	
	@RequestMapping(value ="/create" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create User", notes = "Create and return user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Message create(@RequestBody User user, HttpServletRequest request)
			throws InternalServerException, UserAlreadyExistsException {
		try {
				userService.addUser(user);

		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		Message message  = new Message();
		message.setMessage("Account Created Successfully");
		return message;
	}
	

	

	
}
