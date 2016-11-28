package scolomfr.web.tests.model.vocabulary.algorithm;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;

public interface Algorithm {

	Result analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;

}