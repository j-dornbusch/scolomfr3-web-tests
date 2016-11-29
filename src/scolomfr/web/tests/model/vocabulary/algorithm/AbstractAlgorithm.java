package scolomfr.web.tests.model.vocabulary.algorithm;

import java.util.Collection;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;

@Lazy
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public abstract class AbstractAlgorithm implements Algorithm {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * scolomfr.web.tests.model.vocabulary.Algorithm#analyse(scolomfr.web.tests.
	 * model.vocabulary.Vocabulary)
	 */
	@Override
	public abstract Result<?> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;
}
