package scolomfr.web.tests.model.vocabulary.algorithm;

import java.util.List;
import java.util.Map;

import org.springframework.web.context.annotation.RequestScope;

import scolomfr.web.tests.model.vocabulary.Vocabulary;

public interface DubiousLangStringDetector extends Algorithm<Map<String, List<String>>> {

	public Map<String, List<String>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;

}