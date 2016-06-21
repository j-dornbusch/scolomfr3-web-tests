package scolomfr3.web.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import scolomfr3.web.tests.model.vocabulary.Vocabulary;

@Controller
public class SearchControler {

	@Autowired
	private Vocabulary skosVocabulary;

	@RequestMapping("/search")
	public ModelAndView displaySearchPage() {

		ModelAndView modelAndView = new ModelAndView("scolomfr3-search");

		return modelAndView;
	}

	@RequestMapping(value = "/search/autocomplete/{query}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String autocompleteOnLabels(@PathVariable String query) {
		return "{\"success\":1}";
	}
}
