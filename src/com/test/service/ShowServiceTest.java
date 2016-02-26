package com.test.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.showflix.app.config.HibernateConfiguration;
import com.showflix.app.dao.entity.User;
import com.showflix.app.dto.Details;
import com.showflix.app.service.IShowService;
import com.showflix.app.service.IUserService;
import com.showflix.app.service.exceptions.ServiceException;

/**
 * JUnit test cases for MovieService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class,classes={ 
		HibernateConfiguration.class})
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional

public class ShowServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	IShowService showService;
	
	@Autowired
	IUserService userService;
	
/*	@Test
	public void testShowService() throws ServiceException{
		Details details = loadMovies();
		showService.insertShowDetails(details);
	}*/
	
	@Test 
	public void testUserService() throws ServiceException{
		User user = new User();
		
		user.setFirstName("Saurabh");
		user.setLastName("Rai");
		user.setUserName("saurabhrai2004");
		user.setPassword("msdfmdshdsh");
		
		userService.assignRoleToNewUser(user, "admin");
		
	}

	
	private Details loadMovies() {
		
		List<Details> details = null;

		Gson gson = new Gson();
		Type type = new TypeToken<List<Details>>() {
		}.getType();
		try {
			 details = gson.fromJson(new FileReader("jsondata2"), type);
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return details.get(0);
		

	}

}
