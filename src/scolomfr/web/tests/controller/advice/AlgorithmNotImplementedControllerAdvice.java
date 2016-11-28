package scolomfr.web.tests.controller.advice;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;

@ControllerAdvice(basePackages = { "scolomfr.web.tests.controller" })
public class AlgorithmNotImplementedControllerAdvice {

	@ExceptionHandler(AlgorithmNotImplementedException.class)
	public ModelAndView handleAlgorithmNotImplementedException(HttpServletRequest request, Exception ex) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Algorithme non implémenté pour ce format");
		modelAndView.setViewName("scolomfr3-exception");
		modelAndView.addObject("currentFormat", Formats.getCurrent());
		modelAndView.addObject("formats", Arrays.asList(Formats.values()));
		modelAndView.addObject("currentVersion", Versions.getCurrent());
		modelAndView.addObject("versions", Arrays.asList(Versions.values()));
		return modelAndView;
	}

}
