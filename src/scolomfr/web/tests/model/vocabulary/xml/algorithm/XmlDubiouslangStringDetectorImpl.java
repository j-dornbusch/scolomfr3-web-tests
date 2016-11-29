package scolomfr.web.tests.model.vocabulary.xml.algorithm;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.DubiousLangStringDetector;

@Component
public class XmlDubiouslangStringDetectorImpl implements DubiousLangStringDetector {

	@Override
	public Result<Map<String, List<String>>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException {

		throw new AlgorithmNotImplementedException();

	}

}
