package scolomfr.web.tests.model.vocabulary.algorithm;

import scolomfr.web.tests.model.vocabulary.Formats;

@FunctionalInterface
public interface AbstractAlgorithmFactory {

	AlgorithmFactory getAlgorithmFactory(Formats format) throws AlgorithmNotImplementedException;

}