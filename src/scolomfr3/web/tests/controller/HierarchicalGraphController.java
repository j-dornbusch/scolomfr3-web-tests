package scolomfr3.web.tests.controller;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scolomfr3.web.tests.model.utils.Tree;
import scolomfr3.web.tests.model.vocabulary.Vocabulary;

@Controller
public class HierarchicalGraphController {

	@Autowired
	private Vocabulary skosVocabulary;

	@RequestMapping("/graphs")
	public ModelAndView completeSkosParsing() {

		ModelAndView modelAndView = new ModelAndView("scolomfr3-graphs");
		modelAndView.addObject("revertedNarrower", skosVocabulary.getRevertedNarrowerRelations());
		modelAndView.addObject("revertedBroader", skosVocabulary.getRevertedBroaderRelations());
		modelAndView.addObject("missingNarrower", skosVocabulary.getMissingNarrowerRelations());
		modelAndView.addObject("missingBroader", skosVocabulary.getMissingBroaderRelations());
		return modelAndView;
	}
}
