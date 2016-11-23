package scolomfr3.web.tests.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import scolomfr3.web.tests.controller.response.Result;
import scolomfr3.web.tests.model.vocabulary.Vocabulary;

@Controller
public class LabelsController {

	@Autowired
	private Vocabulary skosVocabulary;

	@RequestMapping("/labels")
	public ModelAndView labelConcerns() {

		ModelAndView modelAndView = new ModelAndView("scolomfr3-labels");
		modelAndView.addObject("caseConcerns", skosVocabulary.getInconsistentCase());
		modelAndView.addObject("nbListsLowercase", skosVocabulary.getNbListLowercase());
		modelAndView.addObject("nbListsUppercase", skosVocabulary.getNbListUppercase());
		TreeMap<String, List<String>> sortedLanguageConcernsMap = new TreeMap<String, List<String>>(
				skosVocabulary.getDubiousLangStrings());
		modelAndView.addObject("languageConcerns", sortedLanguageConcernsMap);
		TreeMap<String, List<String>> sortedMissingLabelsMap = new TreeMap<String, List<String>>(
				skosVocabulary.getMissingPrefLabels());
		modelAndView.addObject("missingLabels", sortedMissingLabelsMap);
		modelAndView.addObject("page", "labels");
		return modelAndView;
	}

	@RequestMapping(path = "/labels/missing", produces = "application/xml")
	@ResponseBody
	public ResponseEntity<Result> missingLabels() {
		TreeMap<String, ArrayList<String>> sortedMissingLabelsMap = new TreeMap<String, ArrayList<String>>(
				skosVocabulary.getMissingPrefLabels());
		Result result = new Result();
		result.setContent(sortedMissingLabelsMap);
		result.setErrors(sortedMissingLabelsMap.size());
		return new ResponseEntity<Result>(result, HttpStatus.EXPECTATION_FAILED);
	}
}
