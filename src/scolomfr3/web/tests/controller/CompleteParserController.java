package scolomfr3.web.tests.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import scolomfr3.web.tests.model.utils.Triple;
import scolomfr3.web.tests.model.vocabulary.Vocabulary;

@Controller
public class CompleteParserController {

	@Autowired
	private Vocabulary skosVocabulary;

	@RequestMapping("/dump")
	public ModelAndView completeSkosParsing() {

		List<Triple<String, String, String>> vocabularyContent = skosVocabulary.getCompleteContent();

		return new ModelAndView("scolomfr3-dump", "vocabularyContent", vocabularyContent);
	}
}
