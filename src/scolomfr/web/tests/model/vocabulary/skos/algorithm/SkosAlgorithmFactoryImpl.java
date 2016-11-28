package scolomfr.web.tests.model.vocabulary.skos.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.algorithm.Algorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmFactory;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;

@Component
public class SkosAlgorithmFactoryImpl implements AlgorithmFactory {

	@Autowired
	SkosDubiouslangStringDetectorImpl skosDubiouslangStringDetector;

	@Autowired
	SkosMissingPrefLabelDetectorImpl skosMissingPrefLabelDetector;

	@Autowired
	SkosInconsistentCaseDetectorImpl skosInconsistentCaseDetector;

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.skos.algorithm.AlgorithmFactory#
	 * getAlgorithm(java.lang.Class)
	 */
	@Override
	public Algorithm getAlgorithm(Class interfaceToImplement) throws AlgorithmNotImplementedException {
		switch (interfaceToImplement.getSimpleName()) {
		case "DubiousLangStringDetector":
			return skosDubiouslangStringDetector;
		case "MissingPrefLabelDetector":
			return skosMissingPrefLabelDetector;
		case "InconsistentCaseDetector":
			return skosInconsistentCaseDetector;
		}
		throw new AlgorithmNotImplementedException();
	}
}
