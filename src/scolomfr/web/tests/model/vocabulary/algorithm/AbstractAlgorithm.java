package scolomfr.web.tests.model.vocabulary.algorithm;

import scolomfr.web.tests.model.vocabulary.Vocabulary;

public abstract class AbstractAlgorithm<T> implements Algorithm<T> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * scolomfr.web.tests.model.vocabulary.Algorithm#analyse(scolomfr.web.tests.
	 * model.vocabulary.Vocabulary)
	 */
	@Override
	public abstract T analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;
}
