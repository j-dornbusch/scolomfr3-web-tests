package scolomfr.web.tests.model.vocabulary.algorithm;

import scolomfr.web.tests.model.vocabulary.Formats;

public interface AbstractAlgorithmFactory {

	AlgorithmFactory getAlgorithmFactory(Formats format) throws AlgorithmNotImplementedException;

}