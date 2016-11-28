package scolomfr.web.tests.model.vocabulary.xml.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.MissingPrefLabelDetector;
import scolomfr.web.tests.model.vocabulary.xml.XmlFormatSelected;

@Component
@Lazy(value = true)
@Conditional(XmlFormatSelected.class)
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@PropertySource("classpath:properties/label.properties")
public class MissingPrefLabelDetectorImpl extends AbstractAlgorithm<Map<String, ArrayList<String>>>
		implements MissingPrefLabelDetector {

	@Override
	public Map<String, ArrayList<String>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException {
		throw new AlgorithmNotImplementedException();
	}

}
