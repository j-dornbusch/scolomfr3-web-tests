package scolomfr.web.tests.model.vocabulary;

import org.apache.jena.rdf.model.Model;

import scolomfr.web.tests.model.vocabulary.algorithm.Algorithm;

public abstract class AbstractVocabulary implements Vocabulary {

	private Model model;

	public Model getModel() {
		return model;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;

	}

	@Override
	public <T> T apply(Algorithm<T> algorithm) {
		return algorithm.analyse(this);
	}

}
