package scolomfr.web.tests.model.vocabulary.algorithm;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import scolomfr.web.tests.model.vocabulary.Vocabulary;

@Lazy
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
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
