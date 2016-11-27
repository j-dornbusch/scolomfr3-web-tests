package scolomfr.web.tests.model.vocabulary;

import org.apache.jena.rdf.model.Model;

import scolomfr.web.tests.model.vocabulary.algorithm.Algorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;

public abstract class AbstractVocabulary implements Vocabulary {

	private Model model;

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;

	}

	@Override
	public <T> T apply(Algorithm<T> algorithm) throws AlgorithmNotImplementedException {
		return algorithm.analyse(this);
	}

}
