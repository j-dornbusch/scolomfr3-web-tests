package scolomfr.web.tests.model.vocabulary.xml.algorithm;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.InconsistentCaseDetector;
import scolomfr.web.tests.model.vocabulary.xml.XmlFormatSelected;

@Component
@Conditional(XmlFormatSelected.class)
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InconsistentCaseDetectorImpl extends AbstractAlgorithm<Map<String, List<String>>>
		implements InconsistentCaseDetector {

	/**
	 * 
	 * @return une map uri du parent -> liste des preflabels problématiques
	 * @throws AlgorithmNotImplementedException
	 */
	@Override
	public Map<String, List<String>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException {
		throw new AlgorithmNotImplementedException();
	}

	@Override
	public int getNbListUppercase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNbListLowercase() {
		// TODO Auto-generated method stub
		return 0;
	}

}
