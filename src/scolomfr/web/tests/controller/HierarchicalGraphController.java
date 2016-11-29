package scolomfr.web.tests.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.AbstractVocabularyFactory;
import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithmFactory;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmFactory;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.MissingRelationDetector;
import scolomfr.web.tests.resources.MissingResourceException;

@Controller
public class HierarchicalGraphController {

	@Autowired
	private AbstractVocabularyFactory vocabularyFactory;

	@Autowired
	private AbstractAlgorithmFactory abstractAlgorithmFactory;

	@RequestMapping("/graphs")
	public ModelAndView graphAnalysis() throws MissingResourceException, AlgorithmNotImplementedException {
		MissingRelationDetector missingRelationDetector = (MissingRelationDetector) algorithmFactory()
				.getAlgorithm(MissingRelationDetector.class);
		Result<Map<String, List<Pair<String, String>>>> missingRelations = (Result<Map<String, List<Pair<String, String>>>>) getCurrentVocabulary()
				.apply(missingRelationDetector);
		ModelAndView modelAndView = new ModelAndView("scolomfr3-graphs");
		modelAndView.addObject("revertedNarrower", getCurrentVocabulary().getRevertedNarrowerRelations());
		modelAndView.addObject("revertedBroader", getCurrentVocabulary().getRevertedBroaderRelations());
		modelAndView.addObject("missingNarrower", missingRelations.getContent().get("narrower"));
		modelAndView.addObject("missingBroader", missingRelations.getContent().get("broader"));
		modelAndView.addObject("page", "graphs");
		return modelAndView;
	}

	private AlgorithmFactory algorithmFactory() throws AlgorithmNotImplementedException {
		return abstractAlgorithmFactory.getAlgorithmFactory(Formats.getCurrent());
	}

	private Vocabulary getCurrentVocabulary() throws MissingResourceException {
		return vocabularyFactory.get(Formats.getCurrent(), Versions.getCurrent());
	}
}
