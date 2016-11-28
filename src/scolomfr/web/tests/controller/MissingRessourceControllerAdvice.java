package scolomfr.web.tests.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;
import scolomfr.web.tests.resources.MissingResourceException;

@ControllerAdvice(basePackages = { "scolomfr.web.tests.controller" })
public class MissingRessourceControllerAdvice {

	@ExceptionHandler(MissingResourceException.class)
	public ModelAndView handleMissingRessourceException(HttpServletRequest request, Exception ex) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", ex.getMessage());
		modelAndView.setViewName("scolomfr3-exception");
		modelAndView.addObject("currentFormat", Formats.getCurrent());
		modelAndView.addObject("formats", Arrays.asList(Formats.values()));
		modelAndView.addObject("currentVersion", Versions.getCurrent());
		modelAndView.addObject("versions", Arrays.asList(Versions.values()));
		return modelAndView;
	}

}
