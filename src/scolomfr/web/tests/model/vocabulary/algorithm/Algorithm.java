package scolomfr.web.tests.model.vocabulary.algorithm;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import scolomfr.web.tests.model.vocabulary.Vocabulary;

@Lazy
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public interface Algorithm<T> {

	T analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;

}