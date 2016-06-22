package scolomfr3.web.tests.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	public @ResponseBody Map<String, String> autocompleteOnLabels(@PathVariable String query) {
		Map<String, String> labels = skosVocabulary.getLabelsForStringPattern(query);
		// ObjectMapper mapper = new ObjectMapper();
		// String json = "";
		// try {
		// json = mapper.writeValueAsString(labels);
		// System.out.println("json " + json);
		// } catch (JsonProcessingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return labels;
	}
}
