package scolomfr.web.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.VocabularyFactory;
import scolomfr.web.tests.resources.MissingResourceException;

@Controller
public class HierarchicalGraphController {

	@Autowired
	private VocabularyFactory vocabularyFactory;

	@RequestMapping("/graphs")
	public ModelAndView graphAnalysis() throws MissingResourceException {

		ModelAndView modelAndView = new ModelAndView("scolomfr3-graphs");
		modelAndView.addObject("revertedNarrower", getCurrentVocabulary().getRevertedNarrowerRelations());
		modelAndView.addObject("revertedBroader", getCurrentVocabulary().getRevertedBroaderRelations());
		modelAndView.addObject("missingNarrower", getCurrentVocabulary().getMissingNarrowerRelations());
		modelAndView.addObject("missingBroader", getCurrentVocabulary().getMissingBroaderRelations());
		modelAndView.addObject("page", "graphs");
		return modelAndView;
	}

	private Vocabulary getCurrentVocabulary() throws MissingResourceException {
		return vocabularyFactory.get(Formats.getCurrent(), Versions.getCurrent());
	}
}
