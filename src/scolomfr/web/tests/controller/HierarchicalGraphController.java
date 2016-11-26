package scolomfr.web.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import scolomfr.web.tests.model.vocabulary.Vocabulary;

@Controller
public class HierarchicalGraphController {

	@Autowired
	private Vocabulary vocabulary;

	@RequestMapping("/graphs")
	public ModelAndView graphAnalysis() {

		ModelAndView modelAndView = new ModelAndView("scolomfr3-graphs");
		modelAndView.addObject("revertedNarrower", vocabulary.getRevertedNarrowerRelations());
		modelAndView.addObject("revertedBroader", vocabulary.getRevertedBroaderRelations());
		modelAndView.addObject("missingNarrower", vocabulary.getMissingNarrowerRelations());
		modelAndView.addObject("missingBroader", vocabulary.getMissingBroaderRelations());
		modelAndView.addObject("page", "graphs");
		return modelAndView;
	}
}
