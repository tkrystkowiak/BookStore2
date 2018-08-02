package pl.jstk.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request, ModelAndView model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		model = new ModelAndView("error");
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				model.addObject("error", "Sorry, you have no access to this page.");
				return model;
			}

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				model.addObject("error", "Sorry, this page does not exist.");
				return model;
			}
			model.addObject("error", statusCode);
			return model;

		}
		model.addObject("error", "It is unclear what happened.");
		return model;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
