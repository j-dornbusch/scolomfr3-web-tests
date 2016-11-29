package scolomfr.web.tests.model.vocabulary.algorithm;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;

@FunctionalInterface
public interface Algorithm {

	Result<?> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;

}