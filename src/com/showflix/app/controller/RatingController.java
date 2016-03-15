package com.showflix.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.showflix.app.controller.exception.InternalServerException;
import com.showflix.app.dao.entity.ShowRating;
import com.showflix.app.dto.Message;
import com.showflix.app.dto.RatingInfo;
import com.showflix.app.service.IRatingService;
import com.showflix.app.service.exceptions.ServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/rating")
@Api(tags = "rating")
public class RatingController {

	@Autowired
	IRatingService ratingService;

	
	@RequestMapping(value = "{imdbId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get Rating for a show", notes = "Returns average rating for a particular show")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public RatingInfo getAverageRatingForShow(@PathVariable("imdbId") String imdbId) throws InternalServerException {
		Double rating = 0.0;
		try {
			rating = ratingService.getAvgRatingForShow(imdbId);
		} catch (ServiceException e) {
			throw new InternalServerException();
		}

		RatingInfo ratingInfo = new RatingInfo(rating);
		return ratingInfo;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add Rating", notes = "Adds Rating for a particular show")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public Message createRating(@RequestBody ShowRating rating, HttpServletRequest request)
			throws InternalServerException {
		try {
			String role = (String) request.getAttribute("role");
			if (role != null && (role.equals("user")||(role.equals("admin")))) {
				rating.setUserName((String) request.getAttribute("user"));
				ratingService.addRating(rating);
			}

		} catch (ServiceException e) {
			throw new InternalServerException();
		}
		Message message = new Message();
		message.setMessage("Show Added Successfully");
		return message;
	}
}
