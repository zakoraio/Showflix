package com.showflix.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.showflix.app.controller.exception.InternalServerException;
import com.showflix.app.controller.exception.UnauthorizedException;
import com.showflix.app.controller.exception.UserAlreadyExistsException;
import com.showflix.app.controller.exception.UserNotFoundException;
import com.showflix.app.dao.entity.User;
import com.showflix.app.dto.Message;
import com.showflix.app.service.IUserService;
import com.showflix.app.service.exceptions.ServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
public class Usercontroller {

	@Autowired
	private IUserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find All Users", notes = "Returns a list of the users in the system.<br>Filters using an optional name parameter")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public List<User> findAll(@RequestParam(required = false, value = "name") String filterByName)
			throws InternalServerException {
		List<User> users = null;
		try {
			users = userService.getUserByFirstName(filterByName);
		} catch (ServiceException e) {
			throw new InternalServerException();
		}

		return users;
	}

	@RequestMapping(value = "{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find User By Id", notes = "Returns a user by it's id if it exists.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public User findOne(@PathVariable("userName") String userName)
			throws UserNotFoundException, InternalServerException {
		User user = null;
		try {
			user = userService.getUserbyUserName(userName);
			if (user == null) {
				throw new UserNotFoundException();
			}
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		return user;

	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create User", notes = "Create and return user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Message create(@RequestBody User user, HttpServletRequest request)
			throws InternalServerException, UserAlreadyExistsException, UnauthorizedException {
		try {
			String role = (String) request.getAttribute("role");
			if (role!=null && role.equals("user")) {
				userService.addUser(user);
			} else
				throw new UnauthorizedException();

		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		Message message = new Message();
		message.setMessage("User successfully added");
		return message;
	}

	@RequestMapping(value = "{userName}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update User", notes = "Update an existing user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Message update(@PathVariable("userName") String userName, @RequestBody User user, HttpServletRequest request)
			throws UserNotFoundException, InternalServerException, UnauthorizedException {
		try {
			if (request.getAttribute("role").equals("user")) {

				User updatedUser = userService.updateUser(user);
				if (updatedUser == null)
					throw new UserNotFoundException();
			} else
				throw new UnauthorizedException();
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		Message message = new Message();
		message.setMessage("User Successfully Updated");
		return message;
	}

	@RequestMapping(value = "{userName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete User", notes = "Delete an existing user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Message delete(@PathVariable("userName") String userName,HttpServletRequest request)
			throws UserNotFoundException, InternalServerException {
		try {
				User user = userService.deleteUser(userName);
				if(user == null){
					throw new UserNotFoundException();
				}
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		Message message = new Message();
		message.setMessage("User Successfully Deleted");
		return message;
	}
}