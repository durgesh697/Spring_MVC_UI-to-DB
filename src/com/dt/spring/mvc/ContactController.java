package com.dt.spring.mvc;

/*
 * Author ----->  Durgesh Tiwari ----> ( 15-Dec-2016)
 *  
 * Controller class
 * 
 */

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dt.spring.service.ContactService;
import com.dt.spring.to.ContactTo;

@RestController

/*
 * Spring 4.0 introduced @RestController A specialized version of the controller
 * which is a convenience annotation that does nothing more than add
 * the @Controller and @ResponseBody annotations. By annotating the controller
 * class with @RestController annotation, you no longer need to
 * add @ResponseBody to all the request mapping methods. The @ResponseBody
 * annotation is active by default.
 
 * @ResponseBody annotation on a method, Spring converts the return value and
 * writes it to the http response automatically. Each method in the Controller
 * class must be annotated with @ResponseBody.
 */

public class ContactController {

	@Autowired
	ContactService cs;

	@RequestMapping(value = { "/showaddcontact.dt" })
	// To maps HTTP requests to handler methods of MVC and REST controllers.
	protected String showContacPage(Map<String, Object> map) throws Exception {
		System.out.println("showContactPage");
		ContactCommand c = new ContactCommand();
		map.put("ct", c);
		return "contact";
	}

	@RequestMapping(value = { "/addcontact" }, method = RequestMethod.POST)
	protected String addContact(@ModelAttribute(value = "ct") ContactCommand ct, HttpServletRequest req)
			throws Exception {
		// Spring MVC supply this object to a Controller method by using the
		// @ModelAttribute annotation:
		System.out.println("addContact");
		ContactTo cto = new ContactTo(ct.getCid(), ct.getCname(), ct.getCemail(), ct.getCphone());
//		ContactTo cto = new ContactTo();

		boolean added = cs.addContact(cto);
		if (added) {
			req.setAttribute("MSG", "Contact added Successfully");
		} else {
			req.setAttribute("MSG", "Error while adding Contact");
		}
		List<ContactTo> clist = cs.getAllContacts();
		if (clist != null && clist.size() > 0)
			req.setAttribute("CLIST", clist);
		ct.setCname("");
		ct.setCemail("");
		ct.setCphone("");
		return "contact";
	}

}
