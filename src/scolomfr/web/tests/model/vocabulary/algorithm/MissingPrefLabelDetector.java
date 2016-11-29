package scolomfr.web.tests.model.vocabulary.algorithm;

import java.util.List;
import java.util.Map;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;

public interface MissingPrefLabelDetector extends Algorithm {

	@Override
	public Result<Map<String, List<String>>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;

}