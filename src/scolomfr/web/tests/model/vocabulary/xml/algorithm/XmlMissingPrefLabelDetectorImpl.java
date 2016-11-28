package scolomfr.web.tests.model.vocabulary.xml.algorithm;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.MissingPrefLabelDetector;

@Component
@PropertySource("classpath:properties/label.properties")
public class XmlMissingPrefLabelDetectorImpl extends AbstractAlgorithm implements MissingPrefLabelDetector {

	@Override
	public Result analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException {
		throw new AlgorithmNotImplementedException();
	}

}
