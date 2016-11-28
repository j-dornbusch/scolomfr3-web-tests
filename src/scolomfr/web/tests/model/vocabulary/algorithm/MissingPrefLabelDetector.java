package scolomfr.web.tests.model.vocabulary.algorithm;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;

public interface MissingPrefLabelDetector extends Algorithm {

	@Override
	public Result analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;

}