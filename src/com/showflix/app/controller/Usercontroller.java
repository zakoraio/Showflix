package com.showflix.app.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.showflix.app.controller.exception.InternalServerException;
import com.showflix.app.controller.exception.UserAlreadyExistsException;
import com.showflix.app.controller.exception.UserNotFoundException;
import com.showflix.app.dao.entity.User;
import com.showflix.app.service.IUserService;
import com.showflix.app.service.exceptions.ServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
@Api(tags="users")
public class Usercontroller {
	
	@Autowired
	private IUserService userService;

	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find All Users",
			notes="Returns a list of the users in the system.<br>Filters using an optional name parameter")
	@ApiResponses(value={
			@ApiResponse(code=200, message="Success"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	public List<User> findAll (@RequestParam(required=false, value="name") String filterByName) throws InternalServerException {
		List<User>  users = null;
		try {
			users = userService.getUserByFirstName(filterByName);
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		
		return users;
	}
	
	@RequestMapping(value="{userName}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find User By Id",
	notes="Returns a user by it's id if it exists.")
	@ApiResponses(value={
		@ApiResponse(code=200, message="Success"),
		@ApiResponse(code=404, message="Not Found"),
		@ApiResponse(code=500, message="Internal Server Error")
	})
	public User findOne(@PathVariable("userName") String userName) throws UserNotFoundException, InternalServerException {
		User user = null;
		try {
			user = userService.getUserbyUserName(userName);
			if(user ==null){
				throw new UserNotFoundException();
			}
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		return user;
		
	}
	
	@RequestMapping(method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Create User",
	notes="Create and return user")
	@ApiResponses(value={
		@ApiResponse(code=200, message="Success"),
		@ApiResponse(code=400, message="Bad Request"),
		@ApiResponse(code=500, message="Internal Server Error")
	})
	public String create (@RequestBody User user) throws InternalServerException, UserAlreadyExistsException {
		try {
			userService.addUser(user);
		
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		return "User Added Successfully";
	}
	
	@RequestMapping(value="{userName}", 
			method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Update User",
	notes="Update an existing user")
	@ApiResponses(value={
		@ApiResponse(code=200, message="Success"),
		@ApiResponse(code=400, message="Bad Request"),
		@ApiResponse(code=404, message="Not Found"),
		@ApiResponse(code=500, message="Internal Server Error")
	})
	public String update (@PathVariable("userName") String userName, @RequestBody User user) throws UserNotFoundException, InternalServerException {
		 try {
			 User existingUser = userService.getUserbyUserName(userName);
			 if(existingUser==null){
				 throw new UserNotFoundException();
			 }
			userService.updateUser(user);
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
			return "User Successfully Updated";
	}
	
	@RequestMapping(value="{userName}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Delete User",
	notes="Delete an existing user")
	@ApiResponses(value={
		@ApiResponse(code=200, message="Success"),
		@ApiResponse(code=404, message="Not Found"),
		@ApiResponse(code=500, message="Internal Server Error")
	})
	public String delete (@PathVariable("userName") String userName) throws UserNotFoundException, InternalServerException {
		 try {
			 User existingUser = userService.getUserbyUserName(userName);
			userService.deleteUser(existingUser);
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
			return "User Successfully Deleted";
	}
}