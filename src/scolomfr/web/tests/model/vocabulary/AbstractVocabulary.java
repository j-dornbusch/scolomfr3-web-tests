package scolomfr.web.tests.model.vocabulary;

import org.apache.jena.rdf.model.Model;

public abstract class AbstractVocabulary implements Vocabulary {

	private Model model;

	public Model getModel() {
		return model;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;

	}

}
