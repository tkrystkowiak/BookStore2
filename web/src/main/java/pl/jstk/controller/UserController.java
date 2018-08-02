package pl.jstk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView logging() {
		ModelAndView model = new ModelAndView("login");
		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView afterLogging() {
		ModelAndView model = new ModelAndView("welcome");
		return model;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView loggingOut() {
		ModelAndView model = new ModelAndView("logout");
		return model;
	}

}
