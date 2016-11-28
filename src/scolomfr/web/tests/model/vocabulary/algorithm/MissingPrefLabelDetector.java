package scolomfr.web.tests.model.vocabulary.algorithm;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.Vocabulary;

@Component
@Lazy
public interface MissingPrefLabelDetector extends Algorithm<Map<String, ArrayList<String>>> {

	public Map<String, ArrayList<String>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;

}