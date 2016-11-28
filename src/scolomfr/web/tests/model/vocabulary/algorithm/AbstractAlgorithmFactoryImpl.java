package scolomfr.web.tests.model.vocabulary.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.skos.algorithm.SkosAlgorithmFactoryImpl;
import scolomfr.web.tests.model.vocabulary.xml.algorithm.XmlAlgorithmFactoryImpl;

@Component
public class AbstractAlgorithmFactoryImpl implements AbstractAlgorithmFactory {

	@Autowired
	SkosAlgorithmFactoryImpl skosAlgorithmFactoryImpl;

	@Autowired
	XmlAlgorithmFactoryImpl xmlAlgorithmFactoryImpl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithmFactory#
	 * getAlgorithmFactory(scolomfr.web.tests.model.vocabulary.Formats)
	 */
	@Override
	public AlgorithmFactory getAlgorithmFactory(Formats format) throws AlgorithmNotImplementedException {
		switch (format) {
		case SKOS:
			return skosAlgorithmFactoryImpl;
		case XML:
			return xmlAlgorithmFactoryImpl;
		}
		throw new AlgorithmNotImplementedException();
	}
}
