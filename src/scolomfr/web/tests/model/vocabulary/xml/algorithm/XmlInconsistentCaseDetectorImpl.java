package scolomfr.web.tests.model.vocabulary.xml.algorithm;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.InconsistentCaseDetector;

@Component
public class XmlInconsistentCaseDetectorImpl implements InconsistentCaseDetector {

	/**
	 * 
	 * @return une map uri du parent -> liste des preflabels problématiques
	 * @throws AlgorithmNotImplementedException
	 */
	@Override
	public Result<Map<String, List<String>>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException {
		throw new AlgorithmNotImplementedException();
	}

	@Override
	public int getNbListUppercase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNbListLowercase() {
		// TODO Auto-generated method stub
		return 0;
	}

}
