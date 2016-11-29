package scolomfr.web.tests.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.AbstractVocabularyFactory;
import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithmFactory;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmFactory;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.DubiousLangStringDetector;
import scolomfr.web.tests.model.vocabulary.algorithm.InconsistentCaseDetector;
import scolomfr.web.tests.model.vocabulary.algorithm.MissingPrefLabelDetector;
import scolomfr.web.tests.resources.MissingResourceException;

@Controller
public class LabelsController {

	@Autowired
	private AbstractVocabularyFactory vocabularyFactory;

	@Autowired
	private AbstractAlgorithmFactory abstractAlgorithmFactory;

	@RequestMapping("/labels")
	public ModelAndView labelConcerns() throws AlgorithmNotImplementedException, MissingResourceException {

		ModelAndView modelAndView = new ModelAndView("scolomfr3-labels");
		InconsistentCaseDetector inconsistentCaseDetector = (InconsistentCaseDetector) algorithmFactory()
				.getAlgorithm(InconsistentCaseDetector.class);
		@SuppressWarnings("unchecked")
		Result<Map<String, ArrayList<String>>> inconsistentCase = (Result<Map<String, ArrayList<String>>>) getCurrentVocabulary()
				.apply(inconsistentCaseDetector);
		Map<String, ArrayList<String>> caseConcerns = inconsistentCase.getContent();

		modelAndView.addObject("caseConcerns", caseConcerns);
		modelAndView.addObject("nbListsLowercase", inconsistentCaseDetector.getNbListLowercase());
		modelAndView.addObject("nbListsUppercase", inconsistentCaseDetector.getNbListUppercase());

		DubiousLangStringDetector dubiousLangStringDetector = (DubiousLangStringDetector) algorithmFactory()
				.getAlgorithm(DubiousLangStringDetector.class);
		@SuppressWarnings({ "unchecked" })
		Result<Map<String, List<String>>> dubious = (Result<Map<String, List<String>>>) getCurrentVocabulary()
				.apply(dubiousLangStringDetector);
		Map<String, List<String>> sortedLanguageConcernsMap = new TreeMap<>(dubious.getContent());

		modelAndView.addObject("languageConcerns", sortedLanguageConcernsMap);
		MissingPrefLabelDetector missingPrefLabelDetector = (MissingPrefLabelDetector) algorithmFactory()
				.getAlgorithm(MissingPrefLabelDetector.class);
		@SuppressWarnings("unchecked")
		Result<Map<String, List<String>>> missingPrefLabels = (Result<Map<String, List<String>>>) getCurrentVocabulary()
				.apply(missingPrefLabelDetector);
		Map<String, List<String>> sortedMissingLabelsMap = new TreeMap<>(missingPrefLabels.getContent());
		modelAndView.addObject("missingLabels", sortedMissingLabelsMap);
		modelAndView.addObject("page", "labels");
		return modelAndView;
	}

	private AlgorithmFactory algorithmFactory() throws AlgorithmNotImplementedException {
		return abstractAlgorithmFactory.getAlgorithmFactory(Formats.getCurrent());
	}

	@RequestMapping(path = "/labels/missing", produces = "application/xml")
	@ResponseBody
	public ResponseEntity<Result<Map<String, List<String>>>> missingLabels()
			throws AlgorithmNotImplementedException, MissingResourceException {
		MissingPrefLabelDetector missingPrefLabelDetector = (MissingPrefLabelDetector) algorithmFactory()
				.getAlgorithm(MissingPrefLabelDetector.class);
		@SuppressWarnings("unchecked")
		Result<Map<String, List<String>>> result = (Result<Map<String, List<String>>>) getCurrentVocabulary()
				.apply(missingPrefLabelDetector);
		return new ResponseEntity<>(result, HttpStatus.EXPECTATION_FAILED);
	}

	private Vocabulary getCurrentVocabulary() throws MissingResourceException {
		return vocabularyFactory.get(Formats.getCurrent(), Versions.getCurrent());
	}

}
