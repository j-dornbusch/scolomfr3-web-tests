package scolomfr.web.tests.model.vocabulary.xml.algorithm;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.MissingPrefLabelDetector;

@Component
@PropertySource("classpath:properties/label.properties")
public class XmlMissingPrefLabelDetectorImpl implements MissingPrefLabelDetector {

	@Override
	public Result<Map<String, List<String>>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException {
		throw new AlgorithmNotImplementedException();
	}

}
