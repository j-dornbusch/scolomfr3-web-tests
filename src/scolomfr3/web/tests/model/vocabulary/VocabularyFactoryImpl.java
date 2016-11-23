package scolomfr3.web.tests.model.vocabulary;

import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import scolomfr3.web.tests.model.vocabulary.rdf.RdfVocabulary;
import scolomfr3.web.tests.model.vocabulary.skos.SkosVocabulary;
import scolomfr3.web.tests.resources.ResourcesLoader;

@Component
@Scope("application")
public class VocabularyFactoryImpl implements VocabularyFactory {

	@Autowired
	ResourcesLoader resourcesLoader;

	@Override
	public Vocabulary get(Vocabularies vocabularyIdentifier) {
		Vocabulary vocabulary = null;
		Model model = ModelFactory.createDefaultModel();
		String inputFileName = null;

		switch (vocabularyIdentifier) {
		case SCOLOMFR3_RDF:
			vocabulary = new RdfVocabulary();
			inputFileName = "/scolomfr-v-3/scolomfr.rdf";
			break;
		case SCOLOMFR3_SKOS:
			vocabulary = new SkosVocabulary();
			inputFileName = "/scolomfr-v-3/scolomfr.skos";
			break;
		}
		InputStream in = resourcesLoader.loadResource(inputFileName);
		model.read(in, null);
		vocabulary.setModel(model);
		return vocabulary;
	}

}
