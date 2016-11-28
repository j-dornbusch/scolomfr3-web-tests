package scolomfr.web.tests.model.vocabulary.xml.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.algorithm.Algorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmFactory;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;

@Component
public class XmlAlgorithmFactoryImpl implements AlgorithmFactory {

	@Autowired
	XmlDubiouslangStringDetectorImpl xmlDubiouslangStringDetector;

	@Autowired
	XmlMissingPrefLabelDetectorImpl xmlMissingPrefLabelDetector;

	@Autowired
	XmlInconsistentCaseDetectorImpl xmlInconsistentCaseDetector;

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
			return xmlDubiouslangStringDetector;
		case "MissingPrefLabelDetector":
			return xmlMissingPrefLabelDetector;
		case "InconsistentCaseDetector":
			return xmlInconsistentCaseDetector;
		}
		throw new AlgorithmNotImplementedException();
	}
}
