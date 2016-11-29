package scolomfr.web.tests.model.vocabulary.algorithm;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;

public interface MissingRelationDetector extends Algorithm {

	@Override
	public Result<Map<String, List<Pair<String, String>>>> analyse(Vocabulary vocabulary)
			throws AlgorithmNotImplementedException;

}