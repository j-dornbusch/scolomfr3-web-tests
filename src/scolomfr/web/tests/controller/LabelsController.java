package scolomfr.web.tests.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.DubiousLangStringDetector;
import scolomfr.web.tests.model.vocabulary.algorithm.InconsistentCaseDetector;

@Controller
public class LabelsController implements ApplicationContextAware {

	@Autowired
	private Vocabulary vocabulary;

	@Autowired
	private DubiousLangStringDetector dubiousLangStringDetector;

	private ApplicationContext applicationContext;

	@RequestMapping("/labels")
	public ModelAndView labelConcerns() {

		ModelAndView modelAndView = new ModelAndView("scolomfr3-labels");
		System.out.println(Formats.getCurrent());
		InconsistentCaseDetector inconsistentCaseDetector = applicationContext.getBean(InconsistentCaseDetector.class);
		System.out.println("####################" + inconsistentCaseDetector.getClass());
		Map<String, List<String>> caseConcerns = null;
		try {
			caseConcerns = vocabulary.apply(inconsistentCaseDetector);
		} catch (AlgorithmNotImplementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelAndView.addObject("caseConcerns", caseConcerns);
		modelAndView.addObject("nbListsLowercase", inconsistentCaseDetector.getNbListLowercase());
		modelAndView.addObject("nbListsUppercase", inconsistentCaseDetector.getNbListUppercase());
		TreeMap<String, List<String>> sortedLanguageConcernsMap = null;
		try {
			sortedLanguageConcernsMap = new TreeMap<String, List<String>>(vocabulary.apply(dubiousLangStringDetector));
		} catch (AlgorithmNotImplementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelAndView.addObject("languageConcerns", sortedLanguageConcernsMap);
		TreeMap<String, List<String>> sortedMissingLabelsMap = new TreeMap<String, List<String>>(
				vocabulary.getMissingPrefLabels());
		modelAndView.addObject("missingLabels", sortedMissingLabelsMap);
		modelAndView.addObject("page", "labels");
		return modelAndView;
	}

	@RequestMapping(path = "/labels/missing", produces = "application/xml")
	@ResponseBody
	public ResponseEntity<Result> missingLabels() {
		TreeMap<String, ArrayList<String>> sortedMissingLabelsMap = new TreeMap<String, ArrayList<String>>(
				vocabulary.getMissingPrefLabels());
		Result result = new Result();
		result.setContent(sortedMissingLabelsMap);
		result.setErrors(sortedMissingLabelsMap.size());
		return new ResponseEntity<Result>(result, HttpStatus.EXPECTATION_FAILED);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}
}
