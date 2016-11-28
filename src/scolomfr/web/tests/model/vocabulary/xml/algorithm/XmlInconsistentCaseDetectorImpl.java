package scolomfr.web.tests.model.vocabulary.xml.algorithm;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.InconsistentCaseDetector;

@Component
public class XmlInconsistentCaseDetectorImpl extends AbstractAlgorithm implements InconsistentCaseDetector {

	/**
	 * 
	 * @return une map uri du parent -> liste des preflabels problématiques
	 * @throws AlgorithmNotImplementedException
	 */
	@Override
	public Result analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException {
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
