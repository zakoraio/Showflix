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

import com.showflix.app.constants.ApplicationConstants;
import com.showflix.app.controller.exception.InternalServerException;
import com.showflix.app.controller.exception.NoRatingsFoundException;
import com.showflix.app.controller.exception.ShowDetailsAlreadyExistsException;
import com.showflix.app.controller.exception.ShowDetailsNotFoundException;
import com.showflix.app.controller.exception.UnauthorizedException;
import com.showflix.app.controller.exception.UnknownSourceException;
import com.showflix.app.dao.entity.Comment;
import com.showflix.app.dto.CommentDto;
import com.showflix.app.dto.Details;
import com.showflix.app.dto.Message;
import com.showflix.app.service.IShowService;
import com.showflix.app.service.exceptions.ServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/shows")
@Api(tags = "shows")
public class Showcontroller {

	@Autowired
	private IShowService showService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find All Shows", notes = "Returns a list of the shows in the system.<br>Filters using an optional name parameter")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public List<Details> findAll(@RequestParam(required = false, value = "name") String filterByName,
			HttpServletRequest request)
					throws InternalServerException, ShowDetailsNotFoundException, UnauthorizedException {
		List<Details> shows = null;
		try {
			String role = (String) request.getAttribute(ApplicationConstants.role);
			if (role != null && (role.equals(ApplicationConstants.user) || role.equals(ApplicationConstants.admin))) {
				if (filterByName != null) {
					shows = showService.getShowByname(filterByName);
				} else {
					shows = showService.getAllShows();
				}

				if (shows == null) {
					throw new ShowDetailsNotFoundException();
				}
			} else
				throw new UnauthorizedException();
		} catch (ServiceException e) {
			throw new InternalServerException();
		}

		return shows;
	}

	@RequestMapping(value = "{imdbId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find ShowDetails By Id", notes = "Returns a show by it's imdb id if it exists.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Details findOne(@PathVariable("imdbId") String imdbId, HttpServletRequest request)
			throws ShowDetailsNotFoundException, InternalServerException, UnauthorizedException {
		Details showDetails = null;
		try {
			String role = (String) request.getAttribute(ApplicationConstants.role);
			if (role != null && (role.equals(ApplicationConstants.user) || role.equals(ApplicationConstants.admin))) {
				showDetails = showService.getShowByImdbId(imdbId);
				if (showDetails == null) {
					throw new ShowDetailsNotFoundException();
				}
			} else
				throw new UnauthorizedException();
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		return showDetails;

	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create ShowDetails", notes = "Create and return A Message on success")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Message create(@RequestBody Details details, HttpServletRequest request)
			throws ShowDetailsAlreadyExistsException, InternalServerException, UnauthorizedException {
		try {
			String role = (String) request.getAttribute(ApplicationConstants.role);
			if (role != null && role.equals(ApplicationConstants.admin)) {
				showService.insertShowDetails(details);
			} else
				throw new UnauthorizedException();

		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		Message message = new Message();
		message.setMessage("Show Added Successfully");
		return message;
	}

	@RequestMapping(value = "{imdbId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update ShowDetails", notes = "Update an existing Show")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Message update(@PathVariable("imdbId") String imdbId, @RequestBody Details details,
			HttpServletRequest request)
					throws ShowDetailsNotFoundException, InternalServerException, UnauthorizedException {

		try {
			String role = (String) request.getAttribute(ApplicationConstants.role);
			if (role != null && role.equals(ApplicationConstants.admin)) {
				Details existingShowDetails = showService.getShowByImdbId(imdbId);
				if (existingShowDetails == null) {
					throw new ShowDetailsNotFoundException();
				}
				showService.updateShowDetails(details);
			} else
				throw new UnauthorizedException();
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		Message message = new Message();
		message.setMessage("Show Updated Successfully");
		return message;
	}

	@RequestMapping(value = "{imdbId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete ShowDetails", notes = "Delete an existing show ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Message delete(@PathVariable("imdbId") String imdbId, HttpServletRequest request)
			throws ShowDetailsNotFoundException, InternalServerException, UnauthorizedException {
		String role = (String) request.getAttribute(ApplicationConstants.role);
		if (role != null && role.equals(ApplicationConstants.admin)) {
			try {
				showService.deleteShow(imdbId);
			} catch (ServiceException e) {
				throw new InternalServerException();
			}
		} else
			throw new UnauthorizedException();
		Message message = new Message();
		message.setMessage("Show deleted Successfully");
		return message;
	}

	@RequestMapping(value = "/toprated", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get top rated shows", notes = "Expects param max to restrice the number of "
			+ "shows in result and returns a list of top rated shows along with their ratings")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 400, message = "Bad Request") })
	public List<Details> getTopRatedShows(@RequestParam(value = "max") Integer max,
			@RequestParam(value = "ratingSource") String ratingSource, HttpServletRequest request)
					throws InternalServerException, NoRatingsFoundException, UnknownSourceException,
					UnauthorizedException {
		List<Details> topRatedShows = null;

		try {
			String role = (String) request.getAttribute(ApplicationConstants.role);
			if (role != null && (role.equals(ApplicationConstants.user) || role.equals(ApplicationConstants.admin))) {
				if (ratingSource.equals("imdb")) {
					topRatedShows = showService.getTopRatedShowsByImdbRating(max);
				} else if (ratingSource.equals("showFlix")) {
					topRatedShows = showService.getTopRatedShowsByShowFlixRating(max);
				} else {
					throw new UnknownSourceException();
				}
			} else
				throw new UnauthorizedException();
		} catch (ShowDetailsNotFoundException e) {
			return null;
		} catch (ServiceException e) {
			throw new InternalServerException();
		}

		return topRatedShows;
	}

	@RequestMapping(value = "/comments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add Comment", notes = "Create a comment for a show")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Message addComment(@RequestBody Comment comment, HttpServletRequest request)
			throws InternalServerException, UnauthorizedException {
		try {
			String role = (String) request.getAttribute(ApplicationConstants.role);
			if (role != null && role.equals(ApplicationConstants.admin) || role.equals(ApplicationConstants.user)) {
				showService.addComment(comment);
			} else
				throw new UnauthorizedException();

		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		Message message = new Message();
		message.setMessage("Comment Added Successfully");
		return message;
	}

	@RequestMapping(value = "/comments/{imdbId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find Comments By Id", notes = "Returns all comments for a show if they exist.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public List<CommentDto> getComments(@PathVariable("imdbId") String imdbId, HttpServletRequest request)
			throws ShowDetailsNotFoundException, InternalServerException, UnauthorizedException {
		List<CommentDto> comments = null;
		try {
			String role = (String) request.getAttribute(ApplicationConstants.role);
			if (role != null && (role.equals(ApplicationConstants.user) || role.equals(ApplicationConstants.admin))) {
				comments = showService.getComments(imdbId);
			} else
				throw new UnauthorizedException();
		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		return comments;

	}

}