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
import com.showflix.app.controller.exception.ShowDetailsAlreadyExistsException;
import com.showflix.app.controller.exception.ShowDetailsNotFoundException;
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
	public List<Details> findAll(@RequestParam(required = false, value = "name") String filterByName) throws InternalServerException, ShowDetailsNotFoundException {
		List<Details> shows = null;
		try {
			if(filterByName!=null){
			shows = showService.getShowByname(filterByName);
			}
			else{
				shows = showService.getAllShows();
			}
			
			if(shows == null){
				throw new ShowDetailsNotFoundException();
			}
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
	public Details findOne(@PathVariable("imdbId") String imdbId) throws ShowDetailsNotFoundException, InternalServerException {
		Details showDetails = null;
		try {
			showDetails = showService.getShowByImdbId(imdbId);
			if(showDetails==null){
				throw new ShowDetailsNotFoundException();
			}
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
	public Message create(@RequestBody Details details)
			throws ShowDetailsAlreadyExistsException, InternalServerException {
		try {
			showService.insertShowDetails(details);

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
	public Message update(@PathVariable("imdbId") String imdbId, @RequestBody Details details)
			throws ShowDetailsNotFoundException, InternalServerException {
		try {
			Details existingShowDetails = showService.getShowByImdbId(imdbId);
			if (existingShowDetails == null) {
				throw new ShowDetailsNotFoundException();
			}
			showService.updateShowDetails(details);
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
	public Message delete(@PathVariable("imdbId") String imdbId)
			throws ShowDetailsNotFoundException, InternalServerException {

		try {
			showService.deleteShow(imdbId);
		} catch (ServiceException e) {
			throw new InternalServerException();
		}

		Message message = new Message();
		message.setMessage("Show deleted Successfully");
		return message;
	}
}