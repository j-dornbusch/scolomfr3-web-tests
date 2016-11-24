package scolomfr.web.tests.model.vocabulary;

import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.rdf.RdfVocabulary;
import scolomfr.web.tests.model.vocabulary.skos.SkosVocabulary;
import scolomfr.web.tests.resources.MissingRessourceException;
import scolomfr.web.tests.resources.ResourcesLoader;

@Component
@Scope("application")
public class VocabularyFactoryImpl implements VocabularyFactory {

	@Autowired
	ResourcesLoader resourcesLoader;

	@Override
	public Vocabulary get(Vocabularies vocabularyIdentifier, SchemaVersion version) throws MissingRessourceException {
		Vocabulary vocabulary;
		Model model = ModelFactory.createDefaultModel();
		String inputFileName;

		switch (vocabularyIdentifier) {
		case SCOLOMFR3_RDF:
			vocabulary = new RdfVocabulary();
			inputFileName = "/scolomfr-v-3.0/scolomfr.rdf";
			break;
		case SCOLOMFR3_SKOS:
			vocabulary = new SkosVocabulary();
			inputFileName = "/scolomfr-v-3.0/scolomfr.skos";
			break;
		default:
			throw new MissingRessourceException(
					"No file is provided for vocabulary identifier" + vocabularyIdentifier.toString());
		}
		InputStream in = resourcesLoader.loadResource(inputFileName);
		if (null == in) {
			throw new MissingRessourceException("File " + inputFileName + " is missing for vocabulary identifier"
					+ vocabularyIdentifier.toString());
		}
		model.read(in, null);
		vocabulary.setModel(model);
		return vocabulary;
	}

}
