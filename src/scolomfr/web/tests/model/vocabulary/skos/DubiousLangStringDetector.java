package scolomfr.web.tests.model.vocabulary.skos;

import java.util.List;
import java.util.Map;

import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.Algorithm;

public interface DubiousLangStringDetector extends Algorithm<Map<String, List<String>>> {

	public Map<String, List<String>> analyse(Vocabulary vocabulary);

}