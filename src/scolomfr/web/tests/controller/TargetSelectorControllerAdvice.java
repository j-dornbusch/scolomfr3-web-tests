package scolomfr.web.tests.controller;

import java.util.Arrays;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;

@ControllerAdvice(basePackages = { "scolomfr.web.tests.controller" })
public class TargetSelectorControllerAdvice {

	@ModelAttribute
	public void formatAttributes(Model model) {
		model.addAttribute("currentFormat", Formats.getCurrent());
		model.addAttribute("formats", Arrays.asList(Formats.values()));
	}

	@ModelAttribute
	public void versionAttributes(Model model) {
		model.addAttribute("currentVersion", Versions.getCurrent());
		model.addAttribute("versions", Arrays.asList(Versions.values()));
	}

}
